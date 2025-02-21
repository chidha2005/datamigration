package com.dataeconomy.datamigration.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dataeconomy.datamigration.model.User;


@Repository
public class UserDaoImp  implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public long save(User user) {
		sessionFactory.getCurrentSession().save(user);
		return user.getId();
	}

	@Override
	public User get(long id) {
		return sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Override
	public List<User> list() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.select(root);
		Query<User> query = session.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public void update(long id, User user) {
		Session session = sessionFactory.getCurrentSession();
		User us = session.byId(User.class).load(id);
		us.setUserRole(user.getUserRole());
		us.setEmailid(user.getEmailid());
		session.flush();
	}

	@Override
	public void delete(long id) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.byId(User.class).load(id);
		session.delete(user);
	}
	@Override
	public boolean checklogin(String username,String password)
	{
		boolean resultflag= false;
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.select(root);
		cq.where(cb.and(cb.equal(root.get("userName"),username),cb.equal(root.get("password"), password)));
		Query<User> query = session.createQuery(cq);
		if(query.getResultList()!=null && query.getResultList().size()>0)
			resultflag = true;
		return resultflag;
	}
	@Override
	public User getSelectedUser(String username)
	{
		System.out.println("***username***"+username);
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.select(root);
		cq.where(cb.equal(root.get("userName"),username));
		Query<User> query = session.createQuery(cq);
		return query.getResultList().get(0);
	}


}
