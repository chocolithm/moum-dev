package moum.project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import moum.project.service.BoardService;
import moum.project.service.CollectionService;
import moum.project.service.CollectionStatusService;
import moum.project.service.MaincategoryService;
import moum.project.service.StorageService;
import moum.project.service.SubcategoryService;
import moum.project.service.UserService;
import moum.project.vo.AttachedFile;
import moum.project.vo.Board;
import moum.project.vo.Collection;
import moum.project.vo.CollectionStatus;
import moum.project.vo.Maincategory;
import moum.project.vo.Subcategory;
import moum.project.vo.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/collection")
@RequiredArgsConstructor
public class CollectionController {

  private final CollectionService collectionService;
  private final MaincategoryService maincategoryService;
  private final SubcategoryService subcategoryService;
  private final CollectionStatusService collectionStatusService;
  private final StorageService storageService;
  private final UserService userService;
  private final BoardService boardService;

  private final String folderName = "collection/";
  private final String boardFolderName = "board/";


  @GetMapping("/form")
  public String form(Model model) throws Exception {
    List<Maincategory> maincategoryList = maincategoryService.list();
    List<CollectionStatus> collectionStatusList = collectionStatusService.list();

    model.addAttribute("maincategoryList", maincategoryList);
    model.addAttribute("collectionStatusList", collectionStatusList);

    return "collection/form";
  }

  @PostMapping("/add")
  @ResponseBody
  public String add(
      Collection collection,
      MultipartFile[] files,
      @AuthenticationPrincipal UserDetails userDetails) throws Exception {

    if (userDetails == null) {
      return "login";
    }

    String email = userDetails.getUsername();
    User loginUser = userService.getByEmail(email);

    collection.setUser(loginUser);

    List<AttachedFile> attachedFiles = new ArrayList<>();

    if (files != null) {
      for (MultipartFile file : files) {
        if (file.getSize() == 0) {
          continue;
        }

        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setFileCategory(AttachedFile.COLLECTION);
        attachedFile.setFilename(UUID.randomUUID().toString());
        attachedFile.setOriginFilename(file.getOriginalFilename());

        Map<String, Object> options = new HashMap<>();
        options.put(StorageService.CONTENT_TYPE, file.getContentType());

        storageService.upload(
            folderName + attachedFile.getFilename(),
            file.getInputStream(),
            options);

        attachedFiles.add(attachedFile);
      }
    }

    collection.setAttachedFiles(attachedFiles);

    if (collectionService.add(collection)) {
      return "success";
    }
    return "failure";
  }

  @GetMapping("/view")
  public String view(int no, Model model) throws Exception {
    Collection collection = collectionService.get(no);
    List<Maincategory> maincategoryList = maincategoryService.list();
    List<Subcategory> subcategoryList = subcategoryService.list(collection.getMaincategory().getNo());
    List<CollectionStatus> collectionStatusList = collectionStatusService.list();

    model.addAttribute("collection", collection);
    model.addAttribute("maincategoryList", maincategoryList);
    model.addAttribute("subcategoryList", subcategoryList);
    model.addAttribute("collectionStatusList", collectionStatusList);

    return "collection/view";
  }

  @PutMapping("/update")
  @ResponseBody
  public String update(
      Collection collection,
      MultipartFile[] files,
      @AuthenticationPrincipal UserDetails userDetails) throws Exception {

    if (userDetails == null) {
      return "login";
    }

    String email = userDetails.getUsername();
    User loginUser = userService.getByEmail(email);

    if (loginUser.getNo() != collectionService.get(collection.getNo()).getUser().getNo()) {
      throw new Exception("타인의 수집품을 수정할 수 없습니다.");
    }

    List<AttachedFile> attachedFiles = new ArrayList<>();

    if (files != null) {
      for (MultipartFile file : files) {
        if (file.getSize() == 0) {
          continue;
        }

        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setFileCategory(AttachedFile.COLLECTION);
        attachedFile.setFilename(UUID.randomUUID().toString());
        attachedFile.setOriginFilename(file.getOriginalFilename());

        Map<String, Object> options = new HashMap<>();
        options.put(StorageService.CONTENT_TYPE, file.getContentType());

        storageService.upload(
            folderName + attachedFile.getFilename(),
            file.getInputStream(),
            options);

        attachedFiles.add(attachedFile);
      }
    }

    collection.setAttachedFiles(attachedFiles);

    if (collectionService.update(collection)) {
      return "success";
    }
    return "failure";
  }


  @DeleteMapping("/delete")
  @ResponseBody
  public String delete(
      int no,
      @AuthenticationPrincipal UserDetails userDetails) throws Exception {

    if (userDetails == null) {
      return "login";
    }

    String email = userDetails.getUsername();
    User loginUser = userService.getByEmail(email);

    Collection collection = collectionService.get(no);

    if (loginUser.getNo() != collection.getUser().getNo()) {
      throw new Exception("타인의 수집품을 삭제할 수 없습니다.");
    }

    for (AttachedFile attachedFile : collection.getAttachedFiles()) {
      storageService.delete(folderName + attachedFile.getFilename());
    }

    if (collectionService.delete(no)) {
      return "success";
    }
    return "failure";
  }



  @DeleteMapping("/deleteFile")
  @ResponseBody
  public String deleteFile(
      int no,
      @AuthenticationPrincipal UserDetails userDetails) throws Exception {

    if (userDetails == null) {
      return "login";
    }

    String email = userDetails.getUsername();
    User loginUser = userService.getByEmail(email);

    Collection collection = collectionService.getByFileNo(no);

    if (loginUser.getNo() != collection.getUser().getNo()) {
      throw new Exception("타인의 수집품을 삭제할 수 없습니다.");
    }

    AttachedFile attachedFile = collectionService.getAttachedFile(no);
    storageService.delete(folderName + attachedFile.getFilename());
    if (collectionService.deleteFile(no)) {
      return "success";
    }
    return "failure";
  }

  private boolean validateColletion(Collection collection) {
    if (collection.getName().trim().isEmpty()) {return false;}
    if (collection.getMaincategory().getNo() == 0) {return false;}
    if (collection.getSubcategory().getNo() == 0) {return false;}
    if (collection.getStorageLocation().trim().isEmpty()) {return false;}
    if (collection.getStatus().getNo() == 0) {return false;}
    return true;
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
      storageService.upload(boardFolderName + attachedFile.getFilename(), file.getInputStream(),
              Map.of(StorageService.CONTENT_TYPE, file.getContentType()));

      // 업로드된 파일을 첨부 파일 리스트에 추가
      attachedFiles.add(attachedFile);
    }

    return attachedFiles;
  }

  @GetMapping("/boardPostForm")
  public String postForm() {
    return "collection/boardPostForm"; // 템플릿 경로
  }

  @PostMapping("/addPost")
  @ResponseBody
  public String addPost(Board board, @RequestParam("files") MultipartFile[] files,
                        @RequestParam(value = "collection.no", required = false) Integer collectionNo, @AuthenticationPrincipal UserDetails userDetails) throws Exception {

    User loginUser = userService.getByEmail(userDetails.getUsername());
    board.setUserNo(loginUser.getNo());

    try {
      // 게시글 종류에 따라 처리
      if ("trade".equals(board.getBoardType())) {
        // 수집품 거래 글인 경우
        // 수집품 정보를 설정
        if (collectionNo != null) {
          Collection collection = collectionService.get(collectionNo);
          board.setCollection(collection);
        }
        // 추가적인 거래 관련 필드 설정
        // 예: 가격, 거래 상태 등
      }

      // 파일 업로드 처리
      List<AttachedFile> attachedFiles = uploadFiles(files);
      board.setAttachedFiles(attachedFiles);

      // 게시글 등록
      boardService.add(board);

      return "success";
    } catch (Exception e) {
      e.printStackTrace();
      return "failure";
    }
  }

}
