package cx.study.auction.dao;

import cx.study.auction.bean.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 *
 * Created by cheng.xiao on 2017/5/4.
 */
public interface AuctionRepository extends JpaRepository<Auction,Integer>,JpaSpecificationExecutor<Auction>
        ,PagingAndSortingRepository<Auction,Integer>{
    List<Auction> findByStatus(int status);
}
