package com.cosmetic.view;

import com.cosmetic.model.Favorite;

/**
 * Created by yujeen on 2017. 11. 7..
 */

public class HomeFavoriteView {
    public static String favoriteWeb;
    public static String hfavoriteImg;
    public HomeFavoriteView(String favorite) {
        if(favorite == Favorite.ETUDE){
            favoriteWeb = "http://m.etudehouse.com/kr/ko/mobile/main.do";
            hfavoriteImg = "http://zizin1318.cafe24.com/homeFavImg/etude.jpg";
        }else if(favorite == Favorite.INNISFREE){
            favoriteWeb = "http://m.innisfree.com/kr/ko/mMain.do";
            hfavoriteImg = "http://zizin1318.cafe24.com/homeFavImg/innisfree.jpg";
        }else if(favorite == Favorite.TONYMOLY){
            favoriteWeb = "http://m.etonymoly.com/html/main.asp";
            hfavoriteImg = "http://zizin1318.cafe24.com/homeFavImg/tonymoly.jpg";
        }else if(favorite == Favorite.MISHA){
            favoriteWeb = "http://m.beautynet.co.kr/missha.do";
            hfavoriteImg = "http://zizin1318.cafe24.com/homeFavImg/misha.jpg";
        }else if(favorite == Favorite.THESAM){
            favoriteWeb = "http://m.thesaemcosmetic.com/";
            hfavoriteImg = "http://zizin1318.cafe24.com/homeFavImg/thesam.png";
        }else if(favorite == Favorite.ARITAUM){
            favoriteWeb = "http://www.aritaum.com/mobile/main.do";
            hfavoriteImg = "http://zizin1318.cafe24.com/homeFavImg/aritaum.jpg";
        }else if(favorite == Favorite.OLIVEYOUNG){
            favoriteWeb = "http://www.oliveyoung.co.kr/store/main/main.do";
            hfavoriteImg = "http://zizin1318.cafe24.com/homeFavImg/oliveyoung.jpg";
        }else if(favorite == Favorite.NATUREREPUBLIC){
            favoriteWeb = "http://www.naturerepublic.com/shop/main";
            hfavoriteImg = "http://zizin1318.cafe24.com/homeFavImg/naturerepublic.jpg";
        }
    }

}
