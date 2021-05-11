package sopra.vol.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import sopra.vol.Application;
import sopra.vol.dao.IReservationDao;
import sopra.vol.model.Reservation;
import sopra.vol.model.StatutReservation;

public class TestDaoFlo {
	public static void main(String[] args) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
	
		IReservationDao reservationDao = Application.getInstance().getReservationDao();

		List<Reservation> reservations = reservationDao.findAll();

		for (Reservation reservation : reservations) {
			System.out.println(reservation);
		}

		System.out.println(reservationDao.findById(5));

		Reservation reservation = new Reservation(1, sdf.parse("12/06/2021"), StatutReservation.CONFIRMER);

		reservationDao.create(reservation);
		
		System.out.println(reservationDao.findById(1));


		reservation.setStatut(StatutReservation.ANNULER);

		reservationDao.update(reservation);
		
		System.out.println(reservationDao.findById(1));

		reservationDao.delete(reservation);
		
		System.out.println(reservationDao.findById(1));
	}
}
