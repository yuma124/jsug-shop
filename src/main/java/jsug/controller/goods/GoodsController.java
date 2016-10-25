package jsug.controller.goods;

import jsug.model.Cart;
import jsug.model.Category;
import jsug.model.Goods;
import jsug.model.OrderLine;
import jsug.service.category.CategoryService;
import jsug.service.goods.GoodsNotFoundException;
import jsug.service.goods.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@Controller
public class GoodsController {

    @Autowired
    GoodsService goodsService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    Cart cart;

    @ModelAttribute("categories")
    List<Category> getCategories() {
        return categoryService.findAll();
    }

    @ModelAttribute
    AddToCartForm getAddToCartForm() {
        return new AddToCartForm();
    }

    @RequestMapping(value = "/")
    String showGoods(@RequestParam(defaultValue = "1") Integer categoryId, @PageableDefault Pageable pageable, Model model) {
        Page<Goods> page = goodsService.findByCategoryId(categoryId, pageable);
        model.addAttribute("page", page);
        model.addAttribute("categoryId", categoryId);
        return "goods/showGoods";
    }

    @RequestMapping(value = "/addToCart", method = RequestMethod.POST)
    String addToCart(@Validated AddToCartForm form, BindingResult result, @PageableDefault Pageable pageable, Model model) {
        if (result.hasErrors()) {
            return showGoods(form.getCategoryId(), pageable, model);
        }
        Goods goods = goodsService.findOne(form.getGoodsId());
        cart.add(OrderLine.builder().goods(goods).quantity(form.getQuantity()).build());
        return "redirect:/cart";
    }

    @ExceptionHandler(GoodsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleGoodsNotFoundException() {
        return "goods/notFound";
    }

}
