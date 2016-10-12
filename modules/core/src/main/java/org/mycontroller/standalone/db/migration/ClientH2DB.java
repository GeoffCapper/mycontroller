/*
 * Copyright 2015-2016 Jeeva Kandasamy (jkandasa@gmail.com)
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mycontroller.standalone.db.migration;

import java.sql.SQLException;

import org.mycontroller.standalone.db.DaoUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Jeeva Kandasamy (jkandasa)
 * @since 0.0.3
 */
@Slf4j
public class ClientH2DB extends ClientBase implements IMigrationClient {

    public void renameColumn(String tableName, String oldColumnName, String newColumnName) throws SQLException {
        if (hasColumn(tableName, oldColumnName)) {
            int dropCount = DaoUtils.getUserDao().getDao().executeRaw(
                    "ALTER TABLE " + tableName + " ALTER COLUMN " + oldColumnName + " RENAME TO " + newColumnName);
            _logger.debug("Renamed OldColumn:{}, NewColumn:{}, Table:{}, Drop count:{}", oldColumnName, newColumnName,
                    tableName, dropCount);
        } else {
            _logger.warn("Selected column[{}] not found! Table:{}", oldColumnName, tableName);
        }
    }

    public void alterColumn(String tableName, String columnName, String columnDefinition) throws SQLException {
        int alterCount = DaoUtils.getUserDao().getDao().executeRaw("ALTER TABLE "
                + tableName + " ALTER COLUMN " + columnName + " " + columnDefinition);
        _logger.debug("Altered column:{}, columnDefinition:{}, table:{}, add count:{}",
                columnName, columnDefinition, tableName, alterCount);
    }

    public void renameTable(String tableName, String newTableName) throws SQLException {
        if (hasTable(tableName)) {
            int changeCount = DaoUtils.getUserDao().getDao().executeRaw(
                    "ALTER TABLE " + tableName + " RENAME TO " + newTableName);
            _logger.debug("Renamed table:{}, NewTable:{}, Change count:{}", tableName, newTableName, changeCount);
        } else {
            _logger.warn("Selected table[{}] not found!", tableName);
        }
    }
}
