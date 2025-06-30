package ambient_intelligence.crud;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import ambient_intelligence.data.ObjectEntity;



public interface ObjectCrud extends MongoRepository<ObjectEntity, String>{

	public List<ObjectEntity> findAllByParent_ObjectId(@Param("parentId") String parentId,Pageable pageable);
	public List<ObjectEntity> findAllByParent_ObjectIdAndActiveTrue(@Param("parentId") String parentId,Pageable pageable);
	public List<ObjectEntity> findAllByActiveTrue(Pageable pageable);
	
	public List<ObjectEntity> findAllByAlias(@Param("alias")String alias,Pageable pageable);
	public List<ObjectEntity> findAllByAliasAndActiveTrue(@Param("alias")String alias,Pageable pageable);
	

	public List<ObjectEntity> findAllByType(@Param("type")String type,Pageable pageable);
	public List<ObjectEntity> findAllByTypeAndActiveTrue(@Param("type")String type,Pageable pageable);
	
	public List<ObjectEntity> findAllByTypeAndStatus(@Param("type")String type,@Param("status")String status,Pageable pageable);
	public List<ObjectEntity> findAllByTypeAndStatusAndActiveTrue(@Param("type")String type,@Param("status")String status,Pageable pageable);


	List<ObjectEntity> findAllByAliasRegex(@Param("alias")String alias, Pageable pageable);
	List<ObjectEntity> findAllByAliasRegexAndActiveTrue(@Param("alias")String alias, Pageable pageable);
}
