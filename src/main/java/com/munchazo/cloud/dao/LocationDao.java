package com.munchazo.cloud.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ThreadLocalSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.munchazo.cloud.persistance.Location;

public class LocationDao {

	
	private SessionFactory sessionFactory;
	
	public LocationDao(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void insertLocation(Location location)
	{
		Session session = sessionFactory.openSession();
		ThreadLocalSessionContext.bind(session);
		session.setFlushMode(FlushMode.COMMIT);
		try
		{
			session.save(location);
		}
		finally{
			session.flush();
			ThreadLocalSessionContext.unbind(sessionFactory);
			session.close();
		}
	}
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Location> getLocations()
	{
		Session session = sessionFactory.openSession();
		ThreadLocalSessionContext.bind(session);
		session.setFlushMode(FlushMode.COMMIT);
		List<Location> locations = new ArrayList<Location>();
		try
		{
			List locationList=session.createQuery("from com.munchazo.cloud.persistance.Location").list();
			for(Object location : locationList)
			{
				locations.add((Location)location);
			}
		}
		finally
		{
			session.flush();
			ThreadLocalSessionContext.unbind(sessionFactory);
			session.close();
		}
		return locations;
	}
}
