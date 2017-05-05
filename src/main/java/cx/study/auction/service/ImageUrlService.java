package cx.study.auction.service;

import cx.study.auction.bean.ImageUrl;
import cx.study.auction.dao.ImageUrlRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Transient;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

/**
 *
 * Created by cheng.xiao on 2017/5/5.
 */
@Service
public class ImageUrlService {
    @Resource
    private ImageUrlRepository imageUrlRepository;

    public List<ImageUrl> findByAuction(Integer id){
        return imageUrlRepository.findAll(new Specification<ImageUrl>() {
            @Override
            public Predicate toPredicate(Root<ImageUrl> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Integer> auctionId = root.get("auction").get("id");
                criteriaQuery.where(criteriaBuilder.equal(auctionId,id));
                return null;
            }
        });
    }

    @Transactional
    public void delete(String id){
        imageUrlRepository.delete(Integer.parseInt(id));
    }
}
