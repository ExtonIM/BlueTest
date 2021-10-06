import pl.blueenergy.document.DocumentDao;

public class Main {
	
	public static void main(String[] args) {
		DocumentDao documentDao = new DocumentDao();
		ProgrammerService programmerService = new ProgrammerService();
		programmerService.execute(documentDao);
	}
}
