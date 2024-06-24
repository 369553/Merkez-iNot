package Base;

public interface IEntityRepository <T extends IEntity> {
    void addToDB(T entity);
    void deleteToDB(T entity);
    void updateToDB(T entity);
}