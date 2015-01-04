package com.iniesta.ardillo.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.iniesta.ardillo.domain.ArdilloConnection;
import com.iniesta.ardillo.util.HibernateUtil;

public class DAOConnection{

	public Integer saveConnection(ArdilloConnection conn){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Integer id = null;
		try {
			transaction = session.beginTransaction();
			id = (Integer) session.save(conn);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	public List<ArdilloConnection> listConnections(){
		List<ArdilloConnection> conns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			conns = session.createQuery("from ArdilloConnection").list();

			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return conns;
	}

	public void updateConnection(ArdilloConnection toUpdate){
		if(toUpdate!=null && toUpdate.getId()!=null){
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				ArdilloConnection conn = (ArdilloConnection) session.get(ArdilloConnection.class, toUpdate.getId());
				conn.update(toUpdate);
				transaction.commit();
			} catch (HibernateException e) {
				transaction.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
		}
	}

	public void deleteConnection(ArdilloConnection conn){
		if(conn!=null && conn.getId()!=null){
			deleteConnection(conn.getId());
		}
	}

	public void deleteConnection(Integer id){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			ArdilloConnection conn = (ArdilloConnection) session.get(ArdilloConnection.class, id);
			session.delete(conn);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteConnection(String name) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			String hql = "delete from ArdilloConnection where name= :name";
			session.createQuery(hql).setString("name", name).executeUpdate();

			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
	}
}