package jsug.app.order;

import jsug.domain.model.Cart;
import jsug.domain.model.Order;
import jsug.domain.service.order.OrderService;
import jsug.domain.service.userdetails.ShopUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 */
@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    Cart cart;

    @RequestMapping(value = "confirm", method = RequestMethod.GET)
    String confirm(@AuthenticationPrincipal ShopUserDetails userDetails, Model model) {
        model.addAttribute("orderLines", cart.getOrderLines());
        if (cart.isEmpty()) {
            model.addAttribute("error", "買い物カゴが空です");
            return "cart/viewCart";
        }
        model.addAttribute("account", userDetails.getAccount());
        model.addAttribute("signature", orderService.calcSignature(cart));
        return "order/confirm";
    }

    @RequestMapping(method = RequestMethod.POST)
    String order(@AuthenticationPrincipal ShopUserDetails userDetails, @RequestParam String signature, RedirectAttributes attributes) {
        Order order = orderService.purchase(userDetails.getAccount(), cart, signature);
        attributes.addFlashAttribute(order);
        return "redirect:/order?finish";
    }

    @RequestMapping(method = RequestMethod.GET, params = "finish")
    String finish() {
        return "order/finish";
    }

}
