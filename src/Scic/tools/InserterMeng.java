package tools;

import dal.*;
import model.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Inserter {

	public static void main(String[] args) throws SQLException {
		// DAO instances.
		UsersDao usersDao = UsersDao.getInstance();
		TrackedPlaceDao trackedPlaceDao = TrackedPlaceDao.getInstance();
		AdministratorDao administratorDao = AdministratorDao.getInstance();
		CommonUsersDao commonUsersDao = CommonUsersDao.getInstance();
		PositionsDao positionsDao = PositionsDao.getInstance();

		// INSERT objects from our model.
		Users user = new Users("Bruce","password","Bruce","C","bruce@mail.com","5555555");
		Users user1 = new Users("Tony","password","Tony","D","tony@mail.com","5555555");
		Users user2 = new Users("Daniel","password","Daniel","K","dan@mail.com","5555555");
		user = usersDao.create(user);
		user1 = usersDao.create(user1);
		user2 = usersDao.create(user2);

		TrackedPlace trackedPlace = new TrackedPlace(user, -122.3656311, 47.67089081);
		TrackedPlace trackedPlace1 = new TrackedPlace(user1, -122.4037018, 47.63554764);
		TrackedPlace trackedPlace2 = new TrackedPlace(user2, -122.3322525, 47.60201263);
		trackedPlace = trackedPlaceDao.create(trackedPlace);
		trackedPlace1 = trackedPlaceDao.create(trackedPlace1);
		trackedPlace2 = trackedPlaceDao.create(trackedPlace2);

		Administrator administrator = new Administrator("Bruce","password","Bruce","C","bruce@mail.com","5555555");
		administrator = administratorDao.create(administrator);

		CommonUsers commonUser = new CommonUsers("Tony","password","Tony","D","tony@mail.com","5555555");
		CommonUsers commonUser1 = new CommonUsers("Daniel","password","Daniel","K","dan@mail.com","5555555");
		commonUser = commonUsersDao.create(commonUser1);
		commonUser1 = commonUsersDao.create(commonUser2);

		Positions position = new Positions(-122.3656311, 47.67089081);
		Positions position1 = new Positions(-122.4037018, 47.63554764);
		Positions position2 = new Positions( -122.3322525, 47.60201263);
		position = positionsDao.create(position);
		position1 = positionsDao.create(position1);
		position2 = positionsDao.create(position2);

		// READ.
		Users us1 = usersDao.getUserByUserName("Bruce");
		System.out.println("Reading Users: " + us1.getUserName());

		TrackedPlace tp1 = trackedPlaceDao.getTrackedPlaceById(trackedPlace1.getTrackID());
		System.out.println("Reading TrackedPlace: ( " + tp1.getLongitude() + ", " + tp1.getLongitude() + " )");

		Administrator admin1 = administratorDao.getAdministrator(administrator);
		System.out.println("Reading Administrator: " + admin1.getUserName());

		CommonUsers cuser1 = commonUsersDao.getCommonUsersByUsername("Tony");
		System.out.println("Reading CommonUsers: " + cuser1.getUserName());

		Positions pos1 = positionsDao.getPositionsById(position.getPositionID());
		System.out.println("Reading Positions: ( " + pos1.getLongitude() + ", " + pos1.getLongitude() + " )");

		//Update
		Users us2 = usersDao.updateLastName(user1, "NEW");
		System.out.println("Updating Users: " + us2.getLastName());

		TrackedPlace tp2 = trackedPlaceDao.updateTrackedPlace(trackedPlace, -122.3656321, 47.67089091);
		System.out.println("Updating TrackedPlace: ( " + tp2.getLongitude() + ", " + tp2.getLongitude() + " )");

		Positions pos2 = positionsDao.updatePosition(position, -122.3656321, 47.67089091);
		System.out.println("Updating Positions: ( " + pos2.getLongitude() + ", " + pos2.getLongitude() + " )");

		//Delete
		Users us3 = usersDao.delete(user2);
		if (Objects.isNull(us3) == true) {
			System.out.println("Delete Users: Success!");
		} else {
			System.out.println("Delete Users: Failed!");
		}

		Administrator admin3 = administratorDao.delete(user);
		if (Objects.isNull(admin3) == true) {
			System.out.println("Delete Administrator: Success!");
		} else {
			System.out.println("Delete Administrator: Failed!");
		}

		TrackedPlace tp3 = trackedPlaceDao.delete(trackedPlace2);
		if (Objects.isNull(tp3) == true) {
			System.out.println("Delete TrackedPlace: Success!");
		} else {
			System.out.println("Delete TrackedPlace: Failed!");
		}

		Positions pos3 = positionsDao.delete(position2);
		if (Objects.isNull(pos3) == true) {
			System.out.println("Delete Positions: Success!");
		} else {
			System.out.println("Delete Positions: Failed!");
		}




	}
}
