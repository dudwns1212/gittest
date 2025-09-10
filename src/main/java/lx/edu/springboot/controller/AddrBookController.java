package lx.edu.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lx.edu.springboot.dao.AddrBookDAO;
import lx.edu.springboot.vo.AddrBookVO;

@Controller
public class AddrBookController {

	@Autowired
	AddrBookDAO dao;

	
	@RequestMapping("/addrbook_form.do")
	public String form(HttpSession session, @RequestParam(value="id", defaultValue="0") String id) {
		return "addrbook_form";
	}
	
	@RequestMapping("/addrbook_list.do")
	public String list(HttpSession session, HttpServletRequest req) throws Exception {
//		if(session.getAttribute("userId")==null ) {
//			return "login";
//		}
		List<AddrBookVO> list = dao.getDBList();
		req.setAttribute("data", list);
		return "addrbook_list";
	}
	
	/*
	 * @RequestMapping("/addrbook_list.do") public ModelAndView list(HttpSession
	 * session) throws Exception { if(session.getAttribute("userId")==null ) {
	 * return "login"; } ModelAndView result = new ModelAndView(); List<AddrBookVO>
	 * list = dao.getDBList(); result.addObject("data", list);
	 * result.setViewName("addrbook_list"); return result; }
	 */
	
	@RequestMapping("/insert.do")
	public String insert(AddrBookVO vo) throws Exception {
		dao.insertDB(vo);
		System.out.println(vo.getAbName() + "의 주소록 추가완료");
		return "redirect:/addrbook_list.do";
	}
	//Name for argument of type [int] not specified, and parameter name information not available via reflection. Ensure that the compiler uses the '-parameters' flag.]을(를) 발생시켰습니다.
	
	@RequestMapping("/edit.do")
	public String edit(HttpServletRequest req, @RequestParam(value="abId", required = false, defaultValue="0") int abId) throws Exception {
		AddrBookVO vo = new AddrBookVO();
		vo = dao.getDB(abId);
		System.out.println(abId + "번 수정화면");
		req.setAttribute("ab", vo);
		return "addrbook_edit_form";
	}
	
	@RequestMapping("/modify.do")
	public String update(AddrBookVO vo) throws Exception {
		dao.updateDB(vo);
		System.out.println(vo.getAbId() + "번 수정완료");
		return "redirect:/addrbook_list.do";
	}
	
	@RequestMapping("/delete.do")
	public String delete(HttpServletRequest req, @RequestParam(value="abId")int abId) throws Exception {
		dao.deleteDB(abId);
		System.out.println(abId + "번 삭제완료");
		return "redirect:/addrbook_list.do";
	}
	
}
