package cx.study.auction;

import cx.study.auction.service.AuctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * Created by cheng.xiao on 2017/5/5.
 */
@Component
public class AuctionStartRunner implements CommandLineRunner{
    private Logger logger = LoggerFactory.getLogger(AuctionStartRunner.class);
    @Resource
    private AuctionService auctionService;
    @Override
    public void run(String... strings) throws Exception {
        logger.info("初始化本地拍品状态");
        auctionService.startTimer();
    }
}
