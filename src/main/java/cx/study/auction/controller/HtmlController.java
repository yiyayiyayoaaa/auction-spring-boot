package cx.study.auction.controller;

import cx.study.auction.bean.*;
import cx.study.auction.dao.ImageUrlRepository;
import cx.study.auction.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * Created by chengxiao on 2017/4/30.
 */
@Controller
public class HtmlController {
    @Resource
    private AuctionTypeService auctionTypeService;
    @Resource
    private CustomerService customerService;
    @Resource
    private AuctionService auctionService;
    @Resource
    private ImageUrlService imageUrlService;
    @Resource
    private AdminService adminService;
    @Resource
    private StatisticsService statisticsService;
    @GetMapping(value = "/index")
    public ModelAndView toIndex(){
        return new ModelAndView("index");
    }

    @GetMapping(value = "/")
    public ModelAndView toLogin(){
        return new ModelAndView("login");
    }

    @GetMapping(value = "/welcome")
    public ModelAndView toWelcome(){
        Statistics statistics = statisticsService.get();
        ModelAndView modelAndView = new ModelAndView("welcome");
        modelAndView.addObject("statistics",statistics);
        return modelAndView;
    }
    /*  拍品管理  */
    @GetMapping(value = "/auction")
    public ModelAndView toAuctionList(){
        return new ModelAndView("auction-list");
    }
    @GetMapping(value = "/auction-category")
    public ModelAndView toAuctionCategory(){
        return new ModelAndView("auction-category");
    }
    @GetMapping(value = "/auction-category-add")
    public ModelAndView toAuctionCategoryAdd(){
        return new ModelAndView("auction-category-add");
    }
    @GetMapping(value = "/auction-category-edit/{id}")
    public ModelAndView toAuctionCategoryEdit(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView("auction-category-edit");
        AuctionType type = auctionTypeService.findById(id);
        modelAndView.addObject("type",type);
        return modelAndView;
    }
    @GetMapping(value = "/auction-add")
    public ModelAndView toAuctionAdd(){
        ModelAndView modelAndView = new ModelAndView("auction-add");
        List<AuctionType> types = auctionTypeService.findAll();
        List<Customer> customers = customerService.findAll();
        modelAndView.addObject("types",types);
        modelAndView.addObject("customers",customers);
        return modelAndView;
    }
    @GetMapping(value = "/auction-edit/{id}")
    public ModelAndView toAuctionEdit(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView("auction-edit");
        List<AuctionType> types = auctionTypeService.findAll();
        List<Customer> customers = customerService.findAll();
        Auction auction = auctionService.findById(id);
        modelAndView.addObject("types",types);
        modelAndView.addObject("customers",customers);
        modelAndView.addObject("auction",auction);
        return modelAndView;
    }
    @GetMapping("/picture-show/{id}")
    public ModelAndView toPictureShow(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView("picture-show");
        List<ImageUrl> imageUrls = imageUrlService.findByAuction(id);
        modelAndView.addObject("images",imageUrls);
        return modelAndView;
    }
    @GetMapping("/auction-setting/{id}")
    public ModelAndView toAuctionSetting(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView("auction-setting");
        Auction auction = auctionService.findById(id);
        modelAndView.addObject("auction",auction);
        return modelAndView;
    }


    /* 用户管理 */
    @GetMapping(value = "/member")
    public String toMemberList(){
        return "member-list";
    }
    @GetMapping(value = "/customer")
    public String toCustomerList(){
        return "customer-list";
    }

    @GetMapping(value = "/customer-add")
    public String toCustomerAdd(){
        return "customer-add";
    }

    @GetMapping(value = "/customer-edit/{id}")
    public ModelAndView toCustomerEdit(@PathVariable Integer id){
        Customer customer = customerService.findById(id);
        ModelAndView modelAndView = new ModelAndView("customer-edit");
        modelAndView.addObject("customer",customer);
        return modelAndView;
    }

    /* 管理员*/
    @GetMapping("/admin")
    public ModelAndView toAdminList(){
        return new ModelAndView("admin-list");
    }
    @GetMapping("/change-password/{id}")
    public ModelAndView toUpdatePassword(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView("change-password");
        Admin admin = adminService.findOne(id);
        modelAndView.addObject("admin",admin);
        return modelAndView;
    }
    @GetMapping("/admin-add")
    public ModelAndView toAdd(){
        return new ModelAndView("admin-add2");
    }
    /* 订单*/
    @GetMapping("/order")
    public ModelAndView toOrderList(){
        return new ModelAndView("order-list");
    }


    @GetMapping("/blank")
    public ModelAndView toBlank(){
        return new ModelAndView("_blank");
    }

}
