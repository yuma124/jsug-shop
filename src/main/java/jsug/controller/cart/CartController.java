package jsug.controller.cart;

import jsug.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 */
@Controller
@RequestMapping("cart")
public class CartController {

    @Autowired
    Cart cart;

    @ModelAttribute
    CartForm setupCartForm() {
        return new CartForm();
    }

    @RequestMapping(method = RequestMethod.GET)
    String viewCart(Model model) {
        model.addAttribute("orderLines", cart.getOrderLines());
        return "cart/viewCart";
    }

    @RequestMapping(method = RequestMethod.POST)
    String removeFromCart(@Validated CartForm cartForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "商品がチェックされていません");
            return viewCart(model);
        }
        cart.remove(cartForm.getLineNo());
        return "redirect:/cart";
    }

}
