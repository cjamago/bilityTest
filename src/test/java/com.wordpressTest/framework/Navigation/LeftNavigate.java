package com.wordpressTest.framework.Navigation;

/**
 * Created by chijiokea on 27/10/2015.
 */
public class LeftNavigate {

    public static class Posts {

        public static class AllPost {

            public static void select() {
                MenuSelector.select("menuPosts", "AllPost");
            }
        }

        public static class addNew {

            public static void Select() {
                MenuSelector.select("menuPosts", "AddNew");
            }
        }
    }

    public static class Pages {
        public static class AllPages {
            public static void select() {

                MenuSelector.select("menuPages", "AllPages");

            }
        }
    }
}
