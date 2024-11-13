package moum.project.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import moum.project.dao.BoardDao;
import moum.project.service.*;
import moum.project.vo.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;
  private final StorageService storageService;
  private final LikesService likesService;
  private final CollectionService collectionService;
  private final AchievementService achievementService;
  private final UserService userService;
  private final CollectionStatusService collectionStatusService;
  private final CommentService commentService;

  private final String boardFolderName = "board/";
  private final BoardDao boardDao;


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

  @GetMapping("/tradeHomeSell")
  public String tradeHomeSell(
          @RequestParam(value = "page", defaultValue = "1") int page,
          @RequestParam(value = "size", defaultValue = "10") int size,
          Model model) throws Exception {

    // 페이지 번호와 사이즈를 기반으로 offset 계산
    int offset = (page - 1) * size;

    // 판매 게시글 목록 조회
    List<Board> tradeSellPosts = boardService.listTradeSellPostsByPage(offset, size);
    model.addAttribute("tradeSellPosts", tradeSellPosts);

    // 총 게시글 수 조회
    int totalTradeSellPosts = boardService.countTradeSellPosts();

    // 총 페이지 수 계산
    int totalPages = (int) Math.ceil((double) totalTradeSellPosts / size);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", totalPages);

    return "board/tradeHomeSell"; // Thymeleaf 템플릿 이름
  }
  // 구매 게시글 페이징 조회
  @GetMapping("/tradeHomeBuy")
  public String tradeHomeBuy(
          @RequestParam(value = "page", defaultValue = "1") int page,
          @RequestParam(value = "size", defaultValue = "10") int size,
          Model model) throws Exception {

    // 페이지 번호와 사이즈를 기반으로 offset 계산
    int offset = (page - 1) * size;

    // 구매 게시글 목록 조회
    List<Board> tradeBuyPosts = boardService.listTradeBuyPostsByPage(offset, size);
    model.addAttribute("tradeBuyPosts", tradeBuyPosts);

    // 총 게시글 수 조회
    int totalTradeBuyPosts = boardService.countTradeBuyPosts();

    // 총 페이지 수 계산
    int totalPages = (int) Math.ceil((double) totalTradeBuyPosts / size);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", totalPages);
    model.addAttribute("size", size); // 템플릿에서 size를 사용하기 위해 추가

    return "board/tradeHomeBuy"; // Thymeleaf 템플릿 이름
  }
  @GetMapping("/tradeHomeButton")
  public String tradeHomeButton() {
    return "board/tradeHomeButton";
  }

  @GetMapping({"/", "/boardHome"})
  public String boardHome(Model model, @AuthenticationPrincipal UserDetails userDetails)
      throws Exception {

    String email = userDetails.getUsername();
    User loginUser = userService.getByEmail(email);
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

    List<Achievement> userRankList = achievementService.listByUserRank();
    model.addAttribute("rankList",
        userRankList); //모델에다가 업적 정보를 가진 userRankList를  list라는 이름으로 담는다.

    Achievement user_achievement_ranklist = achievementService.findRankByUser(
        loginUser.getNo());
    model.addAttribute("rankNowUserList",
        user_achievement_ranklist); //모델에다가 업적 정보를 가진 userRankList를  list라는 이름으로 담는다.

    return "board/boardHome";
  }


  @GetMapping("/boardList")
  public String boardList(@RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "size", defaultValue = "10") int size,
                          Model model) throws Exception {
    int offset = (page - 1) * size;
    List<Board> recentBoards = boardService.listByPage(offset, size);
    model.addAttribute("recentBoards", recentBoards);

    // 페이징 정보 추가
    int totalBoards = boardService.count();
    int totalPages = (int) Math.ceil((double) totalBoards / size);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", totalPages);

    return "board/boardList"; // Thymeleaf 템플릿 이름
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
  public String view(
          @RequestParam("no") int no,
          Model model,
          @AuthenticationPrincipal UserDetails userDetails) throws Exception {

    // 게시글 상세 정보 가져오기
    Board board = boardService.get(no);
    if (board == null) {
      throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다: " + no);
    }

    // 로그인한 사용자 정보 가져오기
    User loginUser = userService.getByEmail(userDetails.getUsername());

    // 게시글 작성자 여부 추가
    model.addAttribute("authenticated", board.getUserNo() == loginUser.getNo());

    model.addAttribute("authenticatedUser", loginUser);


    // 추천수 가져오기
    int likeCount = likesService.countLikesByBoard(no);
    board.setLikeCount(likeCount); // Board 객체에 추천수를 설정 (필드가 있다면)

    // 댓글 목록 가져오기
    List<CommentResponse> comments = commentService.findAllComment(no);

    // 모델에 게시글과 댓글 정보 추가
    model.addAttribute("board", board);
    model.addAttribute("comments", comments);

    return "board/boardView";
  }


  @GetMapping("/update")
  public String updateForm(@RequestParam("no") int no, Model model) throws Exception {
    // 게시글 정보를 가져옴
    Board board = boardService.get(no);
    if (board == null) {
      throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다: " + no);
    }

    // 게시글 종류에 따라 다른 데이터를 모델에 추가
    if ("trade".equals(board.getBoardType())) {
      // 거래 게시글의 경우 추가 정보 설정
      List<CollectionStatus> collectionStatusList = collectionStatusService.list();
      model.addAttribute("collectionStatusList", collectionStatusList);

      List<Collection> collections = collectionService.listAll();
      model.addAttribute("collections", collections);

      model.addAttribute("price", board.getPrice());
      model.addAttribute("transactionType", board.getTradeType());
      model.addAttribute("collection", board.getCollection());
      model.addAttribute("collectionStatus", board.getCollectionStatus());
      model.addAttribute("tradeType", board.getTradeType());
    } else if ("general".equals(board.getBoardType())) {
      // 일반 게시글의 경우 제목, 내용, 공개 여부만 설정
      model.addAttribute("title", board.getTitle());
      model.addAttribute("content", board.getContent());
      model.addAttribute("isPublic", board.isPublic());
    }

    // 공통 속성 추가
    model.addAttribute("board", board);

    return "board/boardUpdateForm"; // 업데이트 폼 페이지로 이동
  }


  @PostMapping("/update/{no}")
  public ResponseEntity<String> update(
      @PathVariable("no") int no,
      Board updatedBoard,
      @RequestParam("files") MultipartFile[] files,
      Model model) throws Exception {

    updatedBoard.setNo(no);
    Board existingBoard = boardService.get(no);

    if (existingBoard == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 게시글을 찾을 수 없습니다: " + no);
    }

    if ("trade".equals(existingBoard.getBoardType())) {
      existingBoard.setPrice(updatedBoard.getPrice());
      existingBoard.setTradeType(updatedBoard.getTradeType());

      if (updatedBoard.getCollectionStatus() != null) {
        existingBoard.setCollectionStatus(updatedBoard.getCollectionStatus());
      }
      if (updatedBoard.getCollection() != null) {
        existingBoard.setCollection(updatedBoard.getCollection());
      }
      boardService.updateTrade(existingBoard);
    } else if ("general".equals(existingBoard.getBoardType())) {
      existingBoard.setTitle(updatedBoard.getTitle());
      existingBoard.setContent(updatedBoard.getContent());
    }

    if (files != null && files.length > 0 && !files[0].isEmpty()) {
      boardService.deleteAttachedFiles(no);
      List<AttachedFile> newFiles = uploadFiles(files);
      for (AttachedFile file : newFiles) {
        file.setBoardNo(no);
      }
      boardService.insertAttachedFiles(newFiles);
      existingBoard.setAttachedFiles(newFiles);
    }

    boolean updateSuccessful = boardService.update(updatedBoard);

    if (updateSuccessful) {
      return ResponseEntity.ok("success");
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 업데이트에 실패했습니다.");
    }
  }


  @PostMapping("/uploadImage")
  @ResponseBody
  public String uploadImage(@RequestParam("file") MultipartFile file) {
    try {
      if (file.isEmpty()) {
        throw new IllegalArgumentException("파일이 비어있습니다.");
      }

      // 파일 업로드 처리
      String originalFilename = file.getOriginalFilename();
      String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
      String uuidFilename = UUID.randomUUID() + extension;
      String filePath = boardFolderName + uuidFilename;

      // NCP 클라우드 스토리지에 업로드
      storageService.upload(
          filePath,
          file.getInputStream(),
          Map.of(StorageService.CONTENT_TYPE, file.getContentType())
      );

      // 이미지 URL 생성
      String imageUrl = "https://kr.object.ncloudstorage.com/bitcamp-moum/" + filePath;
      System.out.println("Generated Image URL: " + imageUrl); // 이미지 URL 콘솔 출력
      return imageUrl;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다.");
    }
  }

//        // 기존 게시글을 가져옴
//        Board existingBoard = boardService.get(board.getNo());
//        if (existingBoard == null) {
//            // 게시글이 없을 경우 예외 발생
//            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다: " + board.getNo());
//        }
//
//        // 기존 게시글의 첨부 파일 목록 가져오기
//        List<AttachedFile> existingFiles = existingBoard.getAttachedFiles();
//        List<AttachedFile> newFiles = new ArrayList<>();
//
//        // 파일 업로드 처리 및 예외 처리
//        try {
//            newFiles = uploadFiles(files);
//        } catch (Exception e) {
//            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.", e);
//        }
//
//        // 새로운 파일이 비어 있지 않으면 기존 파일에 추가
//        if (!newFiles.isEmpty()) {
//            existingFiles.addAll(newFiles);
//        }
//        existingBoard.setAttachedFiles(existingFiles);
//
//        // 제목 및 내용 업데이트
//        existingBoard.setTitle(board.getTitle());
//        existingBoard.setContent(board.getContent());
//
//        // 게시글 업데이트 예외 처리
//        try {
//            boardService.update(existingBoard);
//        } catch (Exception e) {
//            throw new RuntimeException("게시글 업데이트 중 오류가 발생했습니다.", e);
//        }
//
//        return "redirect:/board/boardView?no=" + existingBoard.getNo();
//    }

  @PostMapping("/delete")
  public String delete(@RequestParam("no") int no) throws Exception {
    // 게시글과 첨부 파일 삭제
    boardService.delete(no);
    return "redirect:/board/boardList?deleted=true";
  }


  private List<AttachedFile> uploadFiles(MultipartFile[] files) throws Exception {
    List<AttachedFile> attachedFiles = new ArrayList<>();

    for (MultipartFile file : files) {
      if (file.isEmpty()) {
        continue;
      }

      // 파일 크기 제한 (10MB)
      if (file.getSize() > 10 * 1024 * 1024) {  // 10MB 초과 시 예외 처리
        throw new IllegalArgumentException("파일 크기는 10MB를 초과할 수 없습니다.");
      }

      AttachedFile attachedFile = new AttachedFile();

      // 파일 이름에 확장자 포함
      String originalFilename = file.getOriginalFilename();
      String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
      String uuidFilename = UUID.randomUUID() + extension;

      attachedFile.setFilename(uuidFilename);
      attachedFile.setOriginFilename(originalFilename);

      // 파일 업로드 수행
      storageService.upload(
          boardFolderName + attachedFile.getFilename(),
          file.getInputStream(),
          Map.of(
              StorageService.CONTENT_TYPE, file.getContentType()
          )
      );

      attachedFiles.add(attachedFile);
    }

    return attachedFiles;
  }


  @GetMapping("/boardDetailForm")
  public String boardDetailForm(Model model) throws Exception {
    // 수집품 목록 가져오기
    List<Collection> collections = collectionService.listAll();
    model.addAttribute("collections", collections);

    // 수집품 상태 목록 가져오기
    List<CollectionStatus> collectionStatusList = collectionStatusService.list();
    model.addAttribute("collectionStatusList", collectionStatusList);

    // **새로운 Board 객체를 모델에 추가**
    model.addAttribute("board", new Board());

    return "board/boardDetailForm";
  }


  @PostMapping("/addDetailPost")
  @ResponseBody
  public String addDetailPost(Board board,
                              @RequestParam("files") MultipartFile[] files,
                              @RequestParam(value = "collection.no", required = false) Integer collectionNo,
                              @RequestParam(value = "price", required = false) Integer price,
                              @RequestParam(value = "tradeType", required = false) String tradeType,
                              @AuthenticationPrincipal UserDetails userDetails,
                              Model model) throws Exception {

    // 현재 로그인한 사용자 정보 가져오기
    User loginUser = userService.getByEmail(userDetails.getUsername());
    board.setUserNo(loginUser.getNo());

    try {
      if ("trade".equals(board.getBoardType())) {
        // 수집품 설정
        if (collectionNo != null) {
          Collection collection = collectionService.get(collectionNo);
          board.setCollection(collection);
        }
        // 가격 설정 (null이면 0으로 기본값 설정)
        board.setPrice(price != null ? price : 0);

        // 판매/구매 설정 ('sell' 또는 'buy')
        board.setTradeType(tradeType);

        // 거래 상태를 '거래중'으로 설정 (false가 거래중)
        board.setSellOrSoldStatus(false);
      }

      // 첨부 파일 업로드 및 설정
      List<AttachedFile> attachedFiles = uploadFiles(files);
      board.setAttachedFiles(attachedFiles);

      // 게시글 저장
      boardService.add(board);

      return "success";
    } catch (Exception e) {
      e.printStackTrace();
      return "failure";
    }
  }






}
