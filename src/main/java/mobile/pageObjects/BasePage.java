package mobile.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import lombok.extern.slf4j.Slf4j;
import mobile.pageObjects.enums.SwipeDirection;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class BasePage {

    private static final Logger log = LoggerFactory.getLogger(BasePage.class);
    public AndroidDriver driver;
    private final int timeout = 30;

    public BasePage(AndroidDriver d){
        this.driver = d;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    protected WebElement elementToBeClickable(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement visibilityOf(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement visibilityOfElementLocated(By by){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    protected WebElement visibilityOfElementLocated(By by, int time){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected List<WebElement> visibilityOfAllElements(List<WebElement> elements){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }


    protected WebElement scrollDownUntilElementFound(WebDriver driver, WebElement element, int maxSwipes) {
        int swipes = 0;
        while (swipes < maxSwipes) {
            try {
                return  visibilityOf(element);
            } catch (TimeoutException e) {
                swipeScreen(driver, SwipeDirection.DOWN);
                swipes++;
            }
        }

        throw new NoSuchElementException("Element not found after " + maxSwipes + " swipes.");
    }

    protected WebElement scrollUpUntilElementFound(WebDriver driver, WebElement element, int maxSwipes) {
        int swipes = 0;
        while (swipes < maxSwipes) {
            try {
                return  visibilityOf(element);
            } catch (TimeoutException e) {
                swipeScreen(driver, SwipeDirection.UP);
                swipes++;
            }
        }

        throw new NoSuchElementException("Element not found after " + maxSwipes + " swipes.");
    }

    protected WebElement scrollDownUntilElementFound(WebDriver driver, By by, int maxSwipes) {
        int swipes = 0;
        while (swipes < maxSwipes) {
            try {
                return  visibilityOfElementLocated(by, 5);
            } catch ( TimeoutException e) {
                swipeScreen(driver, SwipeDirection.DOWN);
                swipes++;
            }
        }

        throw new NoSuchElementException("Element not found after " + maxSwipes + " swipes.");
    }

    protected WebElement scrollUpUntilElementFound(WebDriver driver, By by, int maxSwipes) {
        int swipes = 0;
        while (swipes < maxSwipes) {
            try {
                return  visibilityOfElementLocated(by);
            } catch (TimeoutException e) {
                swipeScreen(driver, SwipeDirection.UP);
                swipes++;
            }
        }

        throw new NoSuchElementException("Element not found after " + maxSwipes + " swipes.");
    }



    private boolean swipe(WebDriver driver, Point source, Point end){
        try{
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence sequence = new Sequence(finger, 1);
            sequence.addAction(finger.createPointerMove(Duration.ofMillis(0L), PointerInput.Origin.viewport(), source.x, source.y));
            sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
            sequence.addAction(new Pause(finger, Duration.ofMillis(600L)));
            sequence.addAction(finger.createPointerMove(Duration.ofMillis(600L), PointerInput.Origin.viewport(), end.x, end.y) );
            sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));
            ((AppiumDriver)driver).perform(Collections.singletonList(sequence));
            return true;
        }catch (Exception e){
            log.error("Failed to swipe from " + source + " to " + end + ", the exception: " + e);
            return false;
        }
    }

    private boolean swipeScreen(WebDriver driver, SwipeDirection direction){
        boolean isPassScroll = false;
        try{
            Dimension dimension = driver.manage().window().getSize();
            Point pointStart = new Point(dimension.width / 2, dimension.height / 2);
            int scrollDownDivider = 2;
            Point pointEnd = new Point(0,0);
            switch (direction){
                case UP:
                    pointEnd = new Point(dimension.width/2, dimension.height/2 + dimension.height/2/scrollDownDivider);
                    break;
                case DOWN:
                    pointEnd = new Point(dimension.width/2, dimension.height/2- dimension.height/2/scrollDownDivider);
                    break;
                case LEFT:
                    pointEnd = new Point(dimension.width/4, pointStart.y);
                    break;
                case RIGHT:
                    pointEnd = new Point(dimension.width*3/4, pointStart.y);
                    break;
            }
            isPassScroll =  this.swipe(driver, pointStart, pointEnd);

        } catch (Exception e){
            log.error("couldn't swipe in direction " + direction + ", the exception: " + e);
        }

        return isPassScroll;
    }

    public void tapOnElement(WebDriver driver, WebElement element) {
        try {
            // שלב 1: חישוב מיקום **מרכז** האלמנט (ולא הפינה השמאלית-עליונה)
            Point location = element.getLocation(); // מיקום הפינה השמאלית-עליונה
            int centerX = location.getX() + element.getSize().getWidth() / 2;
            int centerY = location.getY() + element.getSize().getHeight() / 2;
            Point tapPoint = new Point(centerX, centerY); // זו נקודת ה־tap שנבצע

            // שלב 2: הגדרת "אצבע" מסוג TOUCH
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

            // יצירת רצף פעולות (Sequence) לביצוע המחווה
            Sequence tap = new Sequence(finger, 1);

            // שלב 3: הזזת האצבע הווירטואלית לנקודת ה־tap (באופן מיידי)
            tap.addAction(finger.createPointerMove(
                    Duration.ZERO,                        // ללא השהייה
                    PointerInput.Origin.viewport(),       // יחסית למסך (ולא לאלמנט)
                    tapPoint.getX(), tapPoint.getY()      // קואורדינטות המטרה
            ));

            // שלב 4: נגיעה במסך (touch down)
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));

            // שלב 5: המתנה קצרה של 200 מילישניות (כדי לדמות tap רגיל, לא מהיר מדי)
            tap.addAction(new Pause(finger, Duration.ofMillis(200)));

            // שלב 6: הרמת האצבע (touch up)
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));

            // שלב 7: ביצוע הרצף בפועל דרך AppiumDriver
            ((AndroidDriver) driver).perform(Collections.singletonList(tap));

        } catch (Exception e) {
            // טיפול בשגיאות – מדפיס שגיאה אם הפעולה נכשלה
            System.out.println("Failed to tap on element, exception: " + e);
        }
    }



}
