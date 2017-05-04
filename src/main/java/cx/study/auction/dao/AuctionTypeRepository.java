package cx.study.auction.dao;

import cx.study.auction.bean.AuctionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * Created by cheng.xiao on 2017/5/3.
 */
public interface AuctionTypeRepository extends JpaRepository<AuctionType,Integer>
        ,PagingAndSortingRepository<AuctionType,Integer>,JpaSpecificationExecutor<AuctionType>{
}
