import pl.blueenergy.document.*;
import pl.blueenergy.organization.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

public class ProgrammerService {

	public void execute(DocumentDao documentDao) {
		print(getAllAnswers(documentDao));
		getAllUsers(documentDao);
		getRightDate(documentDao);
		Thread firstT = new Thread() {
			@Override
			public void run() {
				Date dateStart = new Date();
				float queAnsw = 0;
				List<Document> ques = documentDao.getAllDocumentsInDatabase();
				int i = 0;
				for (Document d : ques) {
					if (ques.get(i).getClass() != Questionnaire.class) {
						break;
					}
					d = ques.get(i);
					i++;
					Questionnaire q2 = (Questionnaire) d;
					float queLoc = 0;
					for (Question item : q2.getQuestions() ) {
						List<String> ansAll = new ArrayList<>(item.getPossibleAnswers());
						queLoc += ansAll.size();
					}

					queAnsw += queLoc;
				}
				print("(Multithreading) Average number of options: " + queAnsw/i);
				Date dateEnd = new Date();
				long between = dateEnd.getTime() - dateStart.getTime();
				System.out.println("Time of execution with multithtreading: " + between);
				System.out.println("__________________________________________________");
			}
		};

		Thread secondT = new Thread() {
			@Override
			public void run() {
				Date dateStart = new Date();
				List<Document> users = documentDao.getAllDocumentsInDatabase();
				ListIterator<Document> iterator = users.listIterator(2);

				for (int i = 0; iterator.hasNext(); i++) {
					ApplicationForHolidays user = (ApplicationForHolidays) iterator.next();
					User listUser =  user.getUserWhoRequestAboutHolidays();
					System.out.println(listUser.getName() + " " + listUser.getSurname());
				}
				Date dateEnd = new Date();
				long between = dateEnd.getTime() - dateStart.getTime();
				System.out.println("(Multithreading) Time of execution with multithreading: " + between);
				System.out.println("__________________________________________________");
			}
		};

		firstT.start();
		secondT.start();
	}

	private void getRightDate(DocumentDao documentDao) {
		Date dateStart = new Date();
		List<Document> users = documentDao.getAllDocumentsInDatabase();
		ListIterator<Document> iterator = users.listIterator(2);
		for (int i = 0; iterator.hasNext(); i++) {
			ApplicationForHolidays user = (ApplicationForHolidays) iterator.next();
			Date since = user.getSince();
			Date to = user.getTo();
			long between = to.getTime() - since.getTime();
			if (between < 0) {
				System.out.println("Wrong date holidays");
			} else {
				System.out.println("Holidays: " + since + " to " + to);
			}
		}
		Date dateEnd = new Date();
		long between = dateEnd.getTime() - dateStart.getTime();
		System.out.println("Time of execution without multithreading: " + between);
		System.out.println("******************************************************");
	}

	private String getAllUsers(DocumentDao documentDao) {
		Date dateStart = new Date();
		List<Document> users = documentDao.getAllDocumentsInDatabase();
		ListIterator<Document> iterator = users.listIterator(2);

		for (int i = 0; iterator.hasNext(); i++) {
			ApplicationForHolidays user = (ApplicationForHolidays) iterator.next();
			User listUser =  user.getUserWhoRequestAboutHolidays();
			System.out.println(listUser.getName() + " " + listUser.getSurname());
		}
		Date dateEnd = new Date();
		long between = dateEnd.getTime() - dateStart.getTime();
		System.out.println("Time of execution without multithreading: " + between);
		System.out.println("******************************************************");
		return "";
	}


	private String getAllAnswers(DocumentDao documentDao) {
		float queAnsw = 0;
		List<Document> ques = documentDao.getAllDocumentsInDatabase();
		int i = 0;
		for (Document d : ques) {
			if (ques.get(i).getClass() != Questionnaire.class) {
				break;
			}
			d = ques.get(i);
			i++;
			Questionnaire q2 = (Questionnaire) d;
			float queLoc = 0;
			for (Question item : q2.getQuestions() ) {
				List<String> ansAll = new ArrayList<>(item.getPossibleAnswers());
				queLoc += ansAll.size();
			}

			queAnsw += queLoc;
		}
		print("Average number of options: " + queAnsw/i);
		print("***************************************************");
		return " ";
	}

	private void print(String message) {
		System.out.println(message);
	}
}

