package Scic.tools;

import Scic.dal.OffensesDao;
import Scic.dal.OfficialUpdatesDao;
import Scic.model.Offenses;
import Scic.model.OfficialUpdates;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import Scic.*;


/**
 * main() runner, used for the app demo.
 * 
 * Instructions:
 * 1. Create a new MySQL schema and then run the CREATE TABLE statements from lecture:
 * http://goo.gl/86a11H.
 * 2. Update ConnectionManager with the correct user, password, and schema.
 */
public class Inserter {

	public static void main(String[] args) throws SQLException, ParseException, FileNotFoundException {

		// DAO instances.
		OfficialUpdatesDao officialUpdatesDao = OfficialUpdatesDao.getInstance();
		OffensesDao offensesDao = OffensesDao.getInstance();

//
//		OfficialUpdates of1 = officialUpdatesDao.create(new OfficialUpdates(0, 1, "bob", "it is a comment", null));
//		officialUpdatesDao.update(of1, "this is an updated comment");
//		OfficialUpdates gettedOfU = officialUpdatesDao.getOfficialUpdatesByOfficialUpdateId(of1.getOfficialUpdateId());

		Offenses o1 = offensesDao.getOffensesByOffenseId(1);
		List<String> ass = offensesDao.getTopKDistrictByOffenseType(10, "ASSAULT");
		List<Offenses> ofs = offensesDao.getTopKOffensesWithMostComments(10);
		List<String> ls = offensesDao.getTopKOffenseType(10);

	}
}
