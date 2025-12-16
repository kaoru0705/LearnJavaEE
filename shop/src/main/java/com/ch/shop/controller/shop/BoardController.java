package com.ch.shop.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ch.shop.dto.Board;
import com.ch.shop.exception.BoardException;
import com.ch.shop.model.board.BoardService;
import com.ch.shop.model.board.BoardServiceImpl;

import lombok.extern.slf4j.Slf4j;


/*
 * 우리가 제작한 MVC 프레임웍에서는 모든 요청마다 1:1 대응하는 컨트롤러를 매핑하는 방식이었으나,
 * 스프링 MVC는 예를 들어 게시판 1개에 대한 목록, 쓰기, 상세보기, 수정, 삭제에 대해 하나의 컨트롤러로 처리가 가능함
 * 왜? 모든 요청마다 1:1 대응하는 클래스 기반이 아니라, 메서드 기반이기 때문...
 * @Controller 만 붙여도 @Bean에 자동으로 등록 다만 BoardController가 어디사는지는 등록해야 한다. @ComponentScan
 * */
@Slf4j
@Controller	// ComponentScan의 대상이 되어 자동으로 인스턴스 Bean 생성
public class BoardController {
	
	@Autowired
	private BoardService boardService;	// DI 준수해야 하므로, 상위객체를 보유
	
//	public void setBoardService(BoardServiceImpl boardService) {
//		this.boardService = boardService;
//	}
	
	
	// 글쓰기 폼 요청 처리 - jsp가 WEB-INF 밑의 위치하였으므로, 브라우저에서 jsp를 직접 접근할 수 없다.
	// 따라서 아래의 컨트롤러의 메서드에서 /board/write.jsp를 매핑 걸자
	@RequestMapping("/board/registform")
	public ModelAndView registForm() {
		// 3단계: 일 시킬 게 없다!!
		// 4단계: 없다.
		
		/*
		 * "/WEB-INF/views/board/write.jsp"이렇게 직접 보낼 순 없어서 /board/result/registform 이렇게 key 값을 보냈지만 이것도 귀찮다.
		 * DispatcherServlet에게 완전한 jsp 경로를 반환하게 되면, 파일명이 바뀔 때, 이 클래스도 영향을 받으므로
		 * 무언가 jsp를 대신할만한 키 등을 구상해야 하는데, 스프링의 창시자인 로드 존슨은 접두어, 접미어를 활용하는 방식을 고안해냄
		 * 따라서 개발자는 전체 파일명 경로 중 변하지 않는다고 생각하는 부분에 대해 접두어, 집미어를 규칙으로 정하여 알맹이만 반환하는
		 * 방법을 쓰면 된다. 이때 하위컨트롤러가 DispatcherServlet에게 정보를 반환할 때는 String형으로 반환해도 되지만, 
		 * ModelAndView라는 객체를 이용할 수도 있다.
		 * 참고로 ModelAndView에는 데이터를 담을 때는 Model객체에 자동으로 담기고, jsp 접두어, 접미어를 제외한 문자열을 넣어둘 때는
		 * View 객체에 담기는데, ModelAndView는 이 두 객체를 합쳐놓은 객체임. 
		 * 여기서는 /WEB-INF/views/가 접두어 .jsp가 접미어
		 */
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/write");
		return mav;	// /board/result/registform
	}
	

	
	
	// 글 목록 페이지 요청 처리
	// 이 URL 요청이 들어오면 이 메서드를 실행하라고 스프링에게 알려주는 주소표
	@RequestMapping("/board/list")
	public ModelAndView getList() {
		// 3단계 수행
		List list = boardService.selectAll();
		
		// 4단계: 결과 저장.. select 문의 경우 저장할 결과가 있다.
		// 현재 컨트롤러에서는 디자인을 담당하면 안되므로, 디자인 영역인 View에서 보여질 결과를 저장해놓자(request 객체에)
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list );	// jsp에서 기다리고 있는 키값을 넣어야 함...
		//WEB-INF/views/board/list.jsp 
		mav.setViewName("board/list");
		
		return mav;
	}
	
	// 글 쓰기 요청 처리
	// 메서드의 매개변수에 VO(Value Object)로 받을 경우
	// 스프링에서 자체적으로 자동 매핑에 의해 파라미터값들을 채워넣는다.
	// 단, 전제 조건? 파라미터명과 VO의 변수명이 반드시 일치해야 한다.
	// DTO와 VO는 비슷하긴 하지만, DTO는 테이블을 반영한 객체이다 보니
	// 클라이언트에 노출되지 않도록 하는 것이 좋기 때문에, 단순히 클라이언트의 파라미터를
	// 받는 것이 목적이라면, DTO보다는 VO를 사용해야 한다..
	@RequestMapping("/board/regist")
	public ModelAndView regist(Board board) {
//		System.out.println("제목은 " + board.getTitle());
//		System.out.println("작성자는 " + board.getWriter());
//		System.out.println("내용은 " + board.getContent());
		
		log.debug("제목은 " + board.getTitle());
		log.debug("작성자는 " + board.getWriter());
		log.debug("내용은 " + board.getContent());
		
		ModelAndView mav = new ModelAndView();
		
		try {	
			boardService.regist(board); 		//3단계: 모델 영역에게 일시키기
			// 성공의 메시지 관련 처리(목록을 보여주기)
			mav.setViewName("redirect:/board/list");			// 요청을 끊고, 새로 목록을 들어오라고 명령..
		} catch(BoardException e){
			log.error(e.getMessage());	// 개발자를 위한 것임..
			// 실패의 메시지 관련 처리...(에러 페이지)
			mav.addObject("msg", e.getMessage());		// request.setAttribute("msg", e.getMessage())
			mav.setViewName("/error/result");		// redirect를 개발자가 명시하지 않으면 스프링에서는 디폴트가 forwarding 임	
		}
		
		return mav;
	}
	
	// 글 상세보기 요청 처리
	// ModelAndView 말고도 이렇게 할 수 있다
	@RequestMapping("/board/detail")
	public String getDetail(int board_id, Model model)	{	// 클라이언트가 전송한 파라미터명과 동일해야 매핑해줌
		// 3단계: 일 시키기
		Board board = boardService.select(board_id);
		model.addAttribute("board", board);		// jsp에서의 key 값과 일치해야 함
		
		return "board/detail";
	}
	
	// 글 수정 요청 처리 RequestMapping과 비슷 Get방식이면 @GetMapping
	@PostMapping("/board/edit")
	public String edit(Board board, Model model) {
		String viewName=null;
		
		log.debug("title is " + board.getTitle());
		log.debug("writer is " + board.getWriter());
		log.debug("content is " + board.getContent());
		log.debug("board_id is " + board.getBoard_id());
		
		
		try {
			boardService.update(board);
			viewName = "redirect:/board/detail?board_id=" + board.getBoard_id();
		} catch (BoardException e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());	// 에러 정보 저장
			viewName="error/result";
		}
		
		return viewName;
	}
	// 글 삭제 요청 처리
	// 예외 처리 여기서 안 하는 방법
	@GetMapping("/board/delete")
	public String delete(int board_id) {
		log.debug("삭제 요청 시 날아온 파라미터값은 " + board_id);
		
		boardService.delete(board_id);
		
		return "redirect:/board/list";
	}
	
	/*
	 * 스프링의 컨트롤러에서는 예외의 발생을 하나의 이벤트로 보고, 이 이벤트를 자동으로 감지하여
	 * 에러를 처리할 수 있는 @ExceptionHandler를 지원해줌
	 * 
	 * 현재 컨트롤러에 명시된 요청을 처리하는 모든 메서드내에서
	 * BoardException이 발생하면 이를 자동으로 감지하여, 아래의 메서드를 호출해줌
	 * 이 때 메서드를 호출해주면서, 매개변수로 예외 객체의 인스턴스를 자동으로 넘겨줌..
	 */
	@ExceptionHandler(BoardException.class)
	public ModelAndView handle(BoardException e) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", e.getMessage());
		mav.setViewName("error/result");
		return mav;
	}
}
