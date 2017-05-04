package cx.study.auction.service;

import cx.study.auction.bean.Auction;
import cx.study.auction.bean.AuctionType;
import cx.study.auction.dao.AuctionTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.expression.Ids;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

/**
 *
 * Created by cheng.xiao on 2017/5/3.
 */
@Service
public class AuctionTypeService {

    @Resource
    private AuctionTypeRepository auctionTypeRepository;

    public AuctionType findById(Integer id){
        return auctionTypeRepository.findOne(id);
    }

    public Page<AuctionType> findAll(int start, int length,String query){
        int page = start/length;
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        PageRequest pageRequest = new PageRequest(page,length,sort);
        return auctionTypeRepository.findAll(new Specification<AuctionType>() {
            @Override
            public Predicate toPredicate(Root<AuctionType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (!StringUtils.isEmpty(query)){
                    Path<String> typeName = root.get("typeName");
                    criteriaQuery.where(criteriaBuilder.like(typeName,"%"+query+"%"));
                }
                return null;
            }
        },pageRequest);
    }

    public List<AuctionType> findAll(){
        return auctionTypeRepository.findAll();
    }
    @Transactional
    public AuctionType save(String typeName){
        AuctionType auctionType = new AuctionType();
        auctionType.setTypeName(typeName);
        auctionType.setCreateTime(new Date().getTime());
        auctionType.setUpdateTime(new Date().getTime());
        return auctionTypeRepository.save(auctionType);
    }
    @Transactional
    public AuctionType update(Integer id,String typeName){
       if (id != null){
           AuctionType type = auctionTypeRepository.findOne(id);
           type.setTypeName(typeName);
           type.setUpdateTime(new Date().getTime());
           return auctionTypeRepository.save(type);
       }
       return null;
    }

    @Transactional
    public void delete(String id){
        auctionTypeRepository.delete(Integer.parseInt(id));
    }

}
