package cx.study.auction.service;

import cx.study.auction.bean.AuctionType;
import cx.study.auction.dao.AuctionTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public Page<AuctionType> findAll(int start, int length){
        int page = start/length;
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        PageRequest pageRequest = new PageRequest(page,length,sort);
        return auctionTypeRepository.findAll(pageRequest);
    }

    public AuctionType save(String typeName){
        AuctionType auctionType = new AuctionType();
        auctionType.setTypeName(typeName);
        auctionType.setCreateTime(new Date().getTime());
        auctionType.setUpdateTime(new Date().getTime());
        return auctionTypeRepository.save(auctionType);
    }
}
