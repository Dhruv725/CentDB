package com.cs5408.query_executor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.cs5408.logging.Logging;
import com.cs5408.file_handler.FIleHandler;
import com.cs5408.file_handler.IFileHandler;
import com.cs5408.model.Constants;
import com.cs5408.model.Database;

public class DropTableExecutor extends QueryExecutor {
	Logging log=new Logging();
	StateOfDatabase stateOfDatabase=new StateOfDatabase();

	private String tableName;

	private IFileHandler handler = null;

	
	@Override
	public void executeQuery(Database database) throws Exception {

		validateQuery(database);
		handler.dropTable(database.getDatabase(), tableName);
		log.log("general",tableName+" deleted in "+database.getDatabase());
		log.log("general",database.getDatabase()+" Status: "+stateOfDatabase.getStatus(database.getDatabase()));

	}

	@Override
	public void validateQuery(Database database) throws Exception {
		if (database == null || database.getDatabase() == null) {
			System.err.println("No Database selected!");
			throw new Exception();
		}

		Path path = Paths.get(Constants.baseFilePath, database.getDatabase());

		if (!Files.exists(path)) {
			System.err.println("Database doesn't exist!");
			throw new Exception();
		}

		path = Paths.get(Constants.baseFilePath, database.getDatabase(), tableName + ".txt");
		if (!Files.exists(path)) {
			System.err.println("Table '" + tableName + "' dosen't exist!");
			throw new Exception();
		}
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public DropTableExecutor(String tableName) {
		super();
		this.tableName = tableName;
		this.handler = new FIleHandler();
	}
}
