package com.mycompany.mysite.controller;

import com.mycompany.mysite.model.MyUserDetails;
import com.mycompany.mysite.model.Note;
import com.mycompany.mysite.model.User;
import com.mycompany.mysite.service.NoteService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping(value = "/notes")
    public String notes(Model model) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getPrincipal().equals("anonymousUser")){
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            model.addAttribute("user" ,user);
        }
        List<Note> notes = noteService.findAll();
        model.addAttribute("notes", notes);
        return "notes";
    }

    @GetMapping(value = "/newnote")
    public String new_note(Model model) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getPrincipal().equals("anonymousUser")){
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            model.addAttribute("user" ,user);
        }
        model.addAttribute("note", new Note());
        return "newnote";
    }

    @PostMapping(value = "/newnote/save")
    public String saveNote(Note note) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getPrincipal().equals("anonymousUser")){
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            note.setAuthor(user);
            noteService.create(note);}
        return "redirect:/notes";
    }

    @GetMapping(value = "/notes/{id}")
    public String note(@PathVariable("id") Long id,Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getPrincipal().equals("anonymousUser")){
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            model.addAttribute("user" ,user);
        }
        model.addAttribute("note", noteService.findById(id));
        return "note";
    }

    @GetMapping(value = "/mynote")
    public String mynote(Model model) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getPrincipal().equals("anonymousUser")){
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            List<Note> notes = noteService.findByAuthorId(user.getId());
            model.addAttribute("notes", notes);
            model.addAttribute("user" ,user);
        }
        return "mynote";
    }
    @GetMapping(value = "/mynote/edit/{id}")
    public String updateNote(@PathVariable("id") Long id,Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        Long userId = user.getId();
        Note note = noteService.findById(id);
        System.out.println(userId + "|"+note.getAuthor().getId());
        model.addAttribute("user" ,user);
        model.addAttribute("note", note);
        /*if(!userId.equals(note.getAuthor().getId())){
            model.addAttribute("info" ,"You have not premission for update this note");
            return "home";
        }*/
        return "editnote";
    }

    @GetMapping(value = "/mynote/delete/{id}")
    public String deleteNote(@PathVariable("id") Long id){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        Long userNowId = user.getId();
        Long userOldId = noteService.findById(id).getAuthor().getId();
        if(userNowId.equals(userOldId)){
            System.out.println("!");
            noteService.deleteById(id);}
            System.out.println("?");

        return "redirect:/notes";
    }


    @PostMapping(value = "mynote/edit/save")
    public String updateNote(Note note) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        Long userNowId = user.getId();
        Long userOldId = note.getAuthor().getId();
        if(userNowId.equals(userOldId)){
            System.out.println("!");
            System.out.println(noteService.create(note));}
            System.out.println("?");


        return "redirect:/notes";
    }


}
