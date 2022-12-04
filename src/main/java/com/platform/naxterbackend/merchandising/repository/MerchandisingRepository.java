package com.platform.naxterbackend.merchandising.repository;

import com.platform.naxterbackend.merchandising.model.Merchandising;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchandisingRepository extends MongoRepository<Merchandising, String> { }
