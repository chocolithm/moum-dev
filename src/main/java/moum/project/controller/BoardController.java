package moum.project.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import moum.project.service.BoardService;
import moum.project.service.CollectionService;
import moum.project.service.LikesService;
import moum.project.service.StorageService;
import moum.project.vo.AttachedFile;
import moum.project.vo.Board;
import moum.project.vo.Collection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final StorageService storageService;
    private final LikesService likesService;
    private final CollectionService collectionService;

    private final String folderName = "board/";

    // 전체 게시글 조회
    @GetMapping("/all")
    public List<Board> getAllPosts() throws Exception {
        return boardService.listAll();
    }

    // 인기 게시글 조회
    @GetMapping("/popular")
    public List<Board> getPopularPosts() throws Exception {
        return boardService.listPopular();
    }

    // 자랑하기 게시글 조회
    @GetMapping("/bragging")
    public List<Board> getBraggingPosts() throws Exception {
        return boardService.listBraggingPosts();
    }

    @GetMapping("/tradeHome")
    public String tradeHome(Model model) throws Exception {
        List<Board> tradePosts = boardService.listTradePosts();
        // 게시글을 3개로 제한
        if (tradePosts.size() > 3) {
            tradePosts = tradePosts.subList(0, 3);
        }
        model.addAttribute("tradePosts", tradePosts);
        return "board/tradeHome";
    }

    @GetMapping({"/", "/boardHome"})
    public String boardHome(Model model) throws Exception {
        // 게시글 목록을 가져옴
        List<Board> allBoards = boardService.list();

        if (allBoards.isEmpty()) {
            // 게시글이 없을 경우 빈 리스트를 추가
            model.addAttribute("popularBoards", Collections.emptyList());
            model.addAttribute("recentBoards", Collections.emptyList());
            model.addAttribute("achievements", Collections.emptyList());
            return "board/boardHome";
        }

        // 인기 게시글과 최신 게시글은 최대 3개까지만 보여줌
        List<Board> popularBoards = allBoards.subList(0, Math.min(3, allBoards.size()));
        List<Board> recentBoards = allBoards.subList(0, Math.min(3, allBoards.size()));

        // 고정된 업적 데이터 추가
        List<Map<String, Object>> achievements = List.of(
                Map.of("rank", 1, "name", "레고 윈터랜드"),
                Map.of("rank", 2, "name", "대디하디"),
                Map.of("rank", 3, "name", "피규어 레이더")
        );

        // 모델에 데이터 추가
        model.addAttribute("popularBoards", popularBoards);
        model.addAttribute("recentBoards", recentBoards);
        model.addAttribute("achievements", achievements);

        return "board/boardHome";
    }

    @GetMapping("/boardList")
    public String boardList(Model model) throws Exception {
        // 모든 게시글 목록을 가져옴
        List<Board> allBoards = boardService.list();

        if (allBoards.isEmpty()) {
            // 게시글이 없을 경우 빈 리스트를 모델에 추가
            model.addAttribute("recentBoards", Collections.emptyList());
            return "board/boardList";
        }

        // 최근 게시글 10개까지만 가져옴
        List<Board> recentBoards = allBoards.subList(0, Math.min(10, allBoards.size()));
        model.addAttribute("recentBoards", recentBoards);

        return "board/boardList";
    }

    @PostMapping("/addGeneral")
    public String add(Board board, @RequestParam("files") MultipartFile[] files) throws Exception {
        // 새 게시글에 사용자 번호 설정 (임시로 1번 사용자 설정)
        board.setUserNo(1);

        // 파일 업로드 및 첨부 파일 정보 설정
        List<AttachedFile> attachedFiles = uploadFiles(files);
        board.setAttachedFiles(attachedFiles);

        // 게시글 추가
        boardService.add(board);
        return "redirect:/board/boardList";
    }

    @GetMapping("/list")
    public String list(Model model) throws Exception {
        // 모든 게시글을 모델에 추가
        List<Board> boardList = boardService.list();
        model.addAttribute("boardList", boardList);
        return "board/boardList";
    }

    @GetMapping("/boardView")
    public String view(@RequestParam("no") int no, Model model) throws Exception {
        // 게시글 상세 정보 가져오기
        Board board = boardService.get(no);
        if (board == null) {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다: " + no);
        }

        // 추천수 가져오기
        int likeCount = likesService.countLikesByBoard(no);
        board.setLikeCount(likeCount); // Board 객체에 추천수를 설정 (필드가 있다면)

        // 모델에 게시글 정보 추가
        model.addAttribute("board", board);
        return "board/boardView";
    }

    @PostMapping("/update")
    public String update(Board board, @RequestParam("files") MultipartFile[] files) throws Exception {
        // 기존 게시글을 가져옴
        Board existingBoard = boardService.get(board.getNo());
        if (existingBoard == null) {
            // 게시글이 없을 경우 예외 발생
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다: " + board.getNo());
        }

        // 기존 게시글의 첨부 파일 목록 가져오기
        List<AttachedFile> existingFiles = existingBoard.getAttachedFiles();
        List<AttachedFile> newFiles = uploadFiles(files);

        // 새로운 파일이 비어 있지 않으면 기존 파일에 추가
        if (!newFiles.isEmpty()) {
            existingFiles.addAll(newFiles);
        }
        existingBoard.setAttachedFiles(existingFiles);

        // 제목 및 내용 업데이트
        existingBoard.setTitle(board.getTitle());
        existingBoard.setContent(board.getContent());

        // 게시글 업데이트
        boardService.update(existingBoard);
        return "redirect:/board/boardView?no=" + existingBoard.getNo();
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("no") int no) throws Exception {
        // 게시글 삭제
        boardService.delete(no);
        return "redirect:/board/boardList";
    }

    private List<AttachedFile> uploadFiles(MultipartFile[] files) throws Exception {
        List<AttachedFile> attachedFiles = new ArrayList<>();

        // 파일 배열을 순회하며 업로드
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                // 파일이 비어 있으면 건너뜀
                continue;
            }

            AttachedFile attachedFile = new AttachedFile();
            attachedFile.setFileCategory(AttachedFile.BOARD); // 파일 카테고리를 게시판 파일로 설정
            attachedFile.setFilename(UUID.randomUUID().toString()); // 파일 이름을 UUID로 생성
            attachedFile.setOriginFilename(file.getOriginalFilename()); // 원본 파일 이름 설정

            // 파일 업로드 수행
            storageService.upload(folderName + attachedFile.getFilename(), file.getInputStream(),
                    Map.of(StorageService.CONTENT_TYPE, file.getContentType()));

            // 업로드된 파일을 첨부 파일 리스트에 추가
            attachedFiles.add(attachedFile);
        }

        return attachedFiles;
    }
//
//    @GetMapping("/postForm")
//    public String postForm() {
//        return "board/postForm"; // 템플릿 경로
//    }
//
//    @PostMapping("/addPost")
//    @ResponseBody
//    public String addPost(Board board, @RequestParam("files") MultipartFile[] files,
//                          @RequestParam(value = "collection.no", required = false) Integer collectionNo) {
//        try {
//            // 게시글 종류에 따라 처리
//            if ("trade".equals(board.getBoardType())) {
//                // 수집품 거래 글인 경우
//                // 수집품 정보를 설정
//                if (collectionNo != null) {
//                    Collection collection = collectionService.get(collectionNo);
//                    board.setCollection(collection);
//                }
//                // 추가적인 거래 관련 필드 설정
//                // 예: 가격, 거래 상태 등
//            }
//
//            // 파일 업로드 처리
//            List<AttachedFile> attachedFiles = uploadFiles(files);
//            board.setAttachedFiles(attachedFiles);
//
//            // 게시글 등록
//            boardService.add(board);
//
//            return "success";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "failure";
//        }
//    }

}
