package projectSDU2.business.nextGenPersistence;

import projectSDU2.technicalServices.persistence.RDBMapper;

import java.sql.ResultSet;

public class CreditRolesMapper extends RDBMapper {
    public CreditRolesMapper(String tableName) {
        super(tableName);
    }

    @Override
    protected Object getObjectFromRecord(int oid, ResultSet resultSet) {
        try{

        }
    }
}
