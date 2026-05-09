package com.Contact.Repository;

import com.Contact.Entity.Contact;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

	List<Contact> findByUserNameOrderByPriorityAsc(String userName);
}

