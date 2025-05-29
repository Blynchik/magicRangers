package dev.blynchik.magicRangers.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import static dev.blynchik.magicRangers.controller.rout.ErrorPageRoutes.ERROR;

@ControllerAdvice(annotations = Controller.class)
@Slf4j
public class UIExceptionHandler {

    /**
     * Перехватывает все исключения AppException и в зависимости
     * от кода ошибки (400 или 500) редиректит на страницу ошибки или добавляет на саму страницу
     */
    @ExceptionHandler(AppException.class)
    public String handleAppException(AppException ex,
                                     HttpServletRequest request) {
        log.error("AppException occurred: {}", ex);
        if (ex.getStatus().is4xxClientError()) {
            request.getSession().setAttribute("globalError", ex.getMessage());
            return "redirect:" + request.getHeader("Referer");
        }
        // можно закомментировать/раскомментировать в зависимости от того, нужно ли показывать актуальное сообщение об ошибке для 500
//        request.getSession().setAttribute("globalError", ex.getMessage());
        return "redirect:" + ERROR;
    }

    /**
     * Перехватывает все исключения Exception и редиректит на страницу с ошибкой
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex,
                                  HttpServletRequest request) {
        log.error("Exception occurred: {}", ex);
        // можно закомментировать/раскомментировать в зависимости от того, нужно ли показывать актуальное сообщение об ошибке для 500
//        request.getSession().setAttribute("globalError", ex.getMessage());
        return "redirect:" + ERROR;
    }

    /**
     * Вклинивается в цепочку обработки Spring MVC и добавляет атрибут globalError в Model, если он есть в HttpSession.
     * Это позволяет тебе просто использовать его в любом шаблоне без явного добавления в контроллере.
     * Автоматически вызывается перед каждым @RequestMapping методом любого контроллера,
     * если он находится в классе, помеченном как @ControllerAdvice
     */
    @ModelAttribute
    public void addGlobalError(Model model, HttpServletRequest request) {
        Object error = request.getSession().getAttribute("globalError");
        if (error != null) {
            model.addAttribute("globalError", error);
            request.getSession().removeAttribute("globalError");
        }
    }
}
