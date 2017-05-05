package cx.study.auction.dao;

import cx.study.auction.bean.ImageUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * Created by chengxiao on 2017/5/4.
 */
public interface ImageUrlRepository extends JpaRepository<ImageUrl,Integer>,JpaSpecificationExecutor<ImageUrl>{
}
