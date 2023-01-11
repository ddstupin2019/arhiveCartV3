package com.example.demo.controller;

import com.example.demo.enity.*;
import com.example.demo.repozitore.CartaRepozitores;
import com.example.demo.repozitore.FilterRepozitores;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("Verified")
public class VerifiedcardsController {

    @Autowired
    CartaRepozitores cartaRepozitores;
    @Autowired
    FilterRepozitores filterRepozitores;

    @RequestMapping(value = "/setCart", method = RequestMethod.GET)
    public String setCart(@RequestParam("cart") MultipartFile file, @RequestParam("sezon") Boolean sezon, @RequestParam("mesto") String mesto, @RequestParam("group") String group, @RequestParam("vid") Integer vid, @RequestParam("relef") Integer relef, @RequestParam("mashtab") Integer mashtab , Model model){
        Carta carta=new Carta();
        String cart = file.getOriginalFilename()+System.currentTimeMillis();
        String filePath = "C:\\Users\\ddstu\\Server\\"+cart;
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            return "asd";
        }
        carta.setCart(cart);
        carta.setSezon(sezon);
        carta.setMesto(mesto);
        carta.setGroup(group);
        carta.setVid(vid);
        carta.setRelef(relef);
        carta.setMashtab(mashtab);


        cartaRepozitores.delete(1);

        return cartaRepozitores.addCardNOV(carta,true);
    }

    @RequestMapping(value = "/getCart", method = RequestMethod.GET)
    public String getCart( @RequestParam("sezon") Boolean sezon, @RequestParam("mashtab") Integer mashtab,
                           @RequestParam("relef") Integer relef,
                           @RequestParam("mesto") String mesto,@RequestParam("group") String group,
                           @RequestParam("vid") Integer vid, @RequestParam("god") Integer god){
        Filters filters=new Filters();
        filters.setGroup(group);
        filters.setMashtab(mashtab);
        filters.setMesto(mesto);
        filters.setRelef(relef);
        filters.setSezon(sezon);
        filters.setVid(vid);
        filters.setGod(god);

        Carta cart=filterRepozitores.getCart(filters);

        return Parse.parseCartTorezout(Poisk.poisk(cart, filters.getMesto()));
    }

    @RequestMapping(value = "/getCartSt", method = RequestMethod.GET)
    public ModelAndView getCartSt(){
        return new ModelAndView("lol/getCart/filter_a", HttpStatus.OK);

    }

}
