import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import common.UrlConstant;

import java.sql.SQLOutput;
import java.util.List;

public class Demo2 {


    public static void main(String[] args) {
        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_78);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象
        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX

        HtmlPage page = null;
        try {
            page = webClient.getPage(UrlConstant.MLOGIN_URL);//尝试加载上面图片例子给出的网页
            System.out.println(page.asXml());
            System.out.println("----------------");
            HtmlForm form = (HtmlForm) page.getByXPath("//form[@action='']").get(0);
            HtmlButton button = (HtmlButton) form.getByXPath("//button[@type='button']").get(0);
            HtmlInput phoneInput = (HtmlInput)(form.getByXPath("//input[@id='phone']").get(0));
            phoneInput.setTextContent("18513024088");
            HtmlInput pwdInput = (HtmlInput)(form.getByXPath("//input[@id='pwd']").get(0));
            pwdInput.setTextContent("TZTSG1992");
            //get div which has a 'name' attribute of 'John'
//            HtmlButton button = (HtmlButton) page.getByXPath("//button[@type='button']").get(0);
//            HtmlButton button =(HtmlButton) elementP.getFirstChild();
            HtmlPage newPage = button.click();
            webClient.waitForBackgroundJavaScript(3000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
            System.out.println(newPage.getUrl());
            System.out.println(newPage.asText());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }
    }
}
