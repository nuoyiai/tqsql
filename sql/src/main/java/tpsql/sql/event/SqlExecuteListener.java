package tpsql.sql.event;

import java.util.EventListener;

public interface SqlExecuteListener extends EventListener {
	void onSqlExecute();
}
