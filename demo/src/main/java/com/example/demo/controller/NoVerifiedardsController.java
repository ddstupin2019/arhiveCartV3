package com.example.demo.controller;

import com.example.demo.enity.Carta;
import com.example.demo.repozitore.CartaRepozitores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("NoVerified")
public class NoVerifiedardsController {

    @Autowired
    CartaRepozitores cartaRepozitores;

    @RequestMapping(value = "/setCartRez", method = RequestMethod.POST)
    public ModelAndView setCartrez(@RequestParam("file") MultipartFile file, @RequestParam("sezon") String sezon,
                                   @RequestParam("mesto") String mesto, @RequestParam("group") String group,
                                   @RequestParam("vid") String vid, @RequestParam("relef") Integer relef,
                                   @RequestParam("mashtab") Integer mashtab , @RequestParam("god") Integer god ,ModelMap modelMap){
        if(file!=null && sezon!=null && mesto!=null && group!=null && vid!=null && relef!=null && mashtab!=null){
            Carta carta=new Carta();
            String cart = System.currentTimeMillis()+"";

            String filePath = "C:\\Users\\ddstu\\Server\\"+cart;

            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                System.out.println("hjlkhjhjkhjhjhjhjhjhjhjhjhjhjhjhj");
                return new ModelAndView("lol/addCartD/addCartRezE", HttpStatus.OK);
            }

            carta.setCart(cart);
            if(sezon.equals("зима")){
                carta.setSezon(true);
            }else{
                carta.setSezon(false);
            }

            carta.setMesto(mesto);
            carta.setGroup(group);
            carta.setGod(god);
            switch (vid){
                case "классика":
                    carta.setVid(1);
                    break;
                case "спринт":
                    carta.setVid(0);
                    break;
                case "лонг":
                    carta.setVid(2);
                    break;
                case "выбор":
                    carta.setVid(3);
                    break;
                case "рогейн":
                    carta.setVid(4);
                    break;
            }
            carta.setRelef(relef);
            carta.setMashtab(mashtab);

            cartaRepozitores.delete(carta.getId());
            cartaRepozitores.addCardNOV(carta, false);
            return new ModelAndView("lol/addCartD/addCartRez", HttpStatus.OK);
        }
            return new ModelAndView("lol/addCartD/addCartRezE", HttpStatus.OK);
    }

    @RequestMapping(value = "/setCart", method = RequestMethod.GET)
    public ModelAndView getCart(){
        return new ModelAndView("lol/addCartD/addCart", HttpStatus.OK);
    }

    @RequestMapping(value = "/getCart", method = RequestMethod.GET)
    public ModelAndView getCart(@RequestParam("password") String pas, Model model){
        if(pas.equals("dimaCrut")){
            String rezout="";

            Carta cart=cartaRepozitores.getNoVIrCart();

            model.addAttribute("provSezon", cart.getSezon());
            model.addAttribute("provMashtab", cart.getMashtab());
            model.addAttribute("provRelef", cart.getRelef());
            model.addAttribute("provMesto", cart.getMesto());
            model.addAttribute("provGroup", cart.getGroup());
            model.addAttribute("provVid", cart.getVid());
            model.addAttribute("provGod", cart.getGod());
            model.addAttribute("provId", cart.getId());

            return new ModelAndView("lol/AdminContr/StartAct", HttpStatus.OK);
        }else{
            return new ModelAndView("lol/AdminContr/StartActExept", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/getCartRezoutT", method = RequestMethod.POST)
    public ModelAndView getCartRezT(@RequestParam("file") MultipartFile file, @RequestParam("sezon") String sezon,
                                    @RequestParam("mesto") String mesto, @RequestParam("group") String group,
                                    @RequestParam("vid") String vid, @RequestParam("relef") Integer relef,
                                    @RequestParam("mashtab") Integer mashtab , Model model){

         try {
             Carta carta = new Carta();
             String cart = System.currentTimeMillis() + file.getOriginalFilename();

             String filePath = "C:\\Users\\ddstu\\Server\\" + cart;


             file.transferTo(new File(filePath));

             carta.setCart(cart);
             if (sezon.equals("зима")) {
                 carta.setSezon(true);
             } else {
                 carta.setSezon(false);
             }

             carta.setMesto(mesto);
             carta.setGroup(group);
             switch (vid) {
                 case "классика":
                     carta.setVid(1);
                     break;
                 case "спринт":
                     carta.setVid(0);
                     break;
                 case "лонг":
                     carta.setVid(2);
                     break;
                 case "выбор":
                     carta.setVid(3);
                     break;
                 case "рогейн":
                     carta.setVid(4);
                     break;
             }
             carta.setRelef(relef);
             carta.setMashtab(mashtab);
             carta.setId(1);

             cartaRepozitores.delete((Integer) model.getAttribute("provId"));
             cartaRepozitores.addCardNOV(carta, true);
             return new ModelAndView("lol/AdminContr/RezAct", HttpStatus.OK);
         }catch (IOException e){
                 System.out.println("hjlkhjhjkhjhjhjhjhjhjhjhjhjhjhjhj");
                 return new ModelAndView("lol/AdminContr/RezAcExept", HttpStatus.OK);
         }
    }

    @RequestMapping(value = "/getCartRezoutF", method = RequestMethod.POST)
    public ModelAndView getCartRezF(@RequestParam("sezon") String sezon,@RequestParam("id") Integer id,
                                    @RequestParam("mesto") String mesto, @RequestParam("group") String group,
                                    @RequestParam("vid") String vid, @RequestParam("relef") Integer relef,
                                    @RequestParam("mashtab") Integer mashtab , Model model){
        cartaRepozitores.delete(id);
        return new ModelAndView("lol/AdminContr/RezActFalse", HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteCart", method = RequestMethod.GET)
    public String deleteCart(@RequestParam("id") int id){
        cartaRepozitores.delete(id);
        return "Удалено"+id;
    }
}
