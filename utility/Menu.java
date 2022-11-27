package utility;

public class Menu {
    private String menuHeader;
    private String leadtext;
    private String[] menuOptions;

    public Menu(String menuHeader, String leadtext, String[] menuOptions){
        this.menuHeader = menuHeader;
        this.leadtext = leadtext;
        this.menuOptions = menuOptions;
    }

    public void printMenu(){
        System.out.println(menuHeader);
        System.out.println(leadtext);
        for (String menuOption : menuOptions){
            System.out.println(menuOption);
        }
    }
}
