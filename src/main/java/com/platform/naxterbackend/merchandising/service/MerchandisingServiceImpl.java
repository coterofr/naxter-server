package com.platform.naxterbackend.merchandising.service;

import com.platform.naxterbackend.merchandising.model.Merchandising;
import com.platform.naxterbackend.merchandising.repository.MerchandisingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MerchandisingServiceImpl implements MerchandisingService {

    @Autowired
    private MerchandisingRepository merchandisingRepository;

    public List<Merchandising> getMerchandisings() {
        return this.merchandisingRepository.findAll();
    }
}
