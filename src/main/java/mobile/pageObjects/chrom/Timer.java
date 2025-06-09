package mobile.pageObjects.chrom;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import mobile.pageObjects.BasePage;
import org.openqa.selenium.WebElement;

public class Timer extends BasePage {

    @AndroidFindBy(id ="com.google.android.deskclock:id/timer_setup_digit_6")
    private WebElement six;
    @AndroidFindBy(id ="com.google.android.deskclock:id/timer_setup_digit_0")
    private WebElement zero;
    @AndroidFindBy(id = "com.google.android.deskclock:id/timer_setup_time")
    private WebElement digits;
    @AndroidFindBy(id ="com.google.android.deskclock:id/fab")
    private WebElement play;
    @AndroidFindBy(id ="com.google.android.deskclock:id/timer_text")
    private WebElement currentDigit;
    @AndroidFindBy(id = "com.google.android.deskclock:id/play_pause")
    private WebElement pause;
    public Timer(AndroidDriver d) {
        super(d);
    }
    public boolean isExistDigits(String text){
        try{
            visibilityOf(digits).isDisplayed();

        }catch (Exception e){
            return false;
        }
        if(digits.getText().equals(text))
            return true;
        else
            return false;
    }
    public boolean clickSeconds(String text){
        elementToBeClickable(six).click();
        zero.click();
        return isExistDigits(text);
    }
    public void PauseTimer(){
        pause.click();
    }
    public void Play(){
        play.click();
    }
    public Integer GetSeconds(){
    return Integer.parseInt(currentDigit.getText());
    }
}
