package ambient_intelligence.logic.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ambient_intelligence.boundary.NewObjectBoundary;
import ambient_intelligence.boundary.ObjectBoundary;
import ambient_intelligence.boundary.ObjectChildIdBoundary;
import ambient_intelligence.controller.ObjectController;
import ambient_intelligence.logic.ObjectService;
import ambient_intelligence.logic.UserService;
import ambient_intelligence.crud.*;
import ambient_intelligence.data.ObjectEntity;
import ambient_intelligence.data.UserEntity;
import ambient_intelligence.data.UserRole;
import ambient_intelligence.excptions.BadRequestException;
import ambient_intelligence.excptions.InvalidInputException;
import ambient_intelligence.excptions.NotFoundException;
import ambient_intelligence.helper.Converter;


@Service
public class ObjectServiceImpl implements ObjectService{

	private ObjectCrud objectCrud;
	private String systemId;
	private UserService ul;
	
	
	public ObjectServiceImpl(ObjectCrud objectCrud, UserService ul) {
		this.objectCrud = objectCrud;
		this.ul = ul;
	}

	@Deprecated
	@Override
	public ObjectBoundary createNewObject(NewObjectBoundary object) 
	{
		throw new BadRequestException("function createNewObject(NewObjectBoundary) is deprecated!");
	}

	@Override
	public ObjectBoundary createNewObject(String userSystemId,String userEmail,NewObjectBoundary object) {
		UserRole accessLvl=this.ul.getUser(UserService.genId(userSystemId, userEmail)).getRole();
		if(accessLvl != UserRole.OPERATOR)
			throw new BadRequestException("Authorization failed!");
		
		ObjectEntity entity = new ObjectEntity(
				systemId +"_"+UUID.randomUUID().toString(),
				object.getType(),
				object.getAlias(),
				object.getStatus(),
				object.isActive(),
				new Date(),
				systemId +"_"+object.getCreatedBy().getUserId().getEmail(),
				object.getDetails()
				); 
	    entity = this.objectCrud.save(entity);
	    return Converter.objectToBoundary(entity);
	}
	@Override
	@Transactional(readOnly = true)
	public List<ObjectBoundary> getAllObjects() {
		 throw new BadRequestException("function getAllObjects()");
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ObjectBoundary> getAllObjects(String userSystemId,String email,int size,int page) {
		UserRole accessLvl=this.ul.getUser(UserService.genId(userSystemId, email)).getRole();
		if(accessLvl == UserRole.ADMIN) {
			throw new BadRequestException("Authorization failed!");
		}else if(accessLvl == UserRole.OPERATOR)
		{
			return this.objectCrud
				.findAll(
						PageRequest.of(page, size))
						.stream() // Stream<Entities>
						.map(Converter::objectToBoundary) // Stream<Boundary>
						.toList(); // List<Boundary>
		}else
			return this.objectCrud
					.findAllByActiveTrue(
							PageRequest.of(page, size))
							.stream() // Stream<Entities>
							.map(Converter::objectToBoundary) // Stream<Boundary>
							.toList(); // List<Boundary>;
		
	}

	@Override
	@Deprecated
	public ObjectBoundary getSpecificObject(String systemId, String objectId) {
		ObjectEntity entity = this.objectCrud.findById(systemId+"_"+objectId).orElseThrow(()->new NotFoundException("Object not Found!"));
		
		//return Converter.userToBoundary(entity);
		return Converter.objectToBoundary(entity);
	}
	@Override
	@Transactional(readOnly = true)
	public ObjectBoundary getSpecificObject(String userSystemId,String email,String objectSystemId,String objectId) {
		String usid=UserService.genId(userSystemId, email);
		UserRole usro = this.ul.getUser(usid).getRole();
		if( usro== UserRole.ADMIN) {
			throw new BadRequestException("Authorization failed");
		}
		ObjectEntity entity = this.objectCrud.findById(objectSystemId+"_"+objectId).orElseThrow(()->new NotFoundException("Object not Found!"));
		if (usro == UserRole.END_USER && !entity.isActive())
			throw new BadRequestException("Authorization failed!");
		//return Converter.userToBoundary(entity);
		return Converter.objectToBoundary(entity);
	}
	
	@Deprecated
	@Override
	public void deleteAll() {
			throw new BadRequestException("function deleteAll is deprecated");
	}
	public void deleteAll(String systemId, String email) {
		if(this.ul.getUser(UserService.genId(systemId, email)).getRole() != UserRole.ADMIN) {
			throw new BadRequestException("access denied");
		}
		this.objectCrud.deleteAll();
	}

	@Override
	@Deprecated
	public void update(String systemId, String objectId, ObjectBoundary boundaryForUpdate) {
		throw new BadRequestException("function update(String,String,ObjectBoundary) is deprecated!");
	}
	
	@Override
	public void update(String userSystemId, String email,String objectSystemId,String objectId, ObjectBoundary boundaryForUpdate) {
		if(this.ul.getUser(UserService.genId(userSystemId, email)).getRole() != UserRole.OPERATOR) {
			throw new BadRequestException("Authorization failed!");
		}
		ObjectEntity existing = this.objectCrud
				.findById(objectSystemId+"_"+objectId)
				.orElseThrow(()->new NotFoundException("No such object found"));
		// no null for boolean
		existing.setActive(boundaryForUpdate.isActive());
		
		if (!boundaryForUpdate.getAlias().isBlank())
			existing.setAlias(boundaryForUpdate.getAlias());
		
		if (!boundaryForUpdate.getStatus().isBlank())
			existing.setStatus(boundaryForUpdate.getStatus());
		
		if (!boundaryForUpdate.getType().isBlank())
			existing.setType(boundaryForUpdate.getType());
		
		if (boundaryForUpdate.getDetails()!=null)
			existing.setDetails(boundaryForUpdate.getDetails());
		
		if (boundaryForUpdate.getCreatedBy()!=null)
			existing.setCreatedBy(
					boundaryForUpdate.getCreatedBy().getUserId().getSystemId()
					+"_"+
					boundaryForUpdate.getCreatedBy().getUserId().getEmail());
		if(boundaryForUpdate.getCreationTimestamp()!=null)
			existing.setCreationTimestamp(boundaryForUpdate.getCreationTimestamp());
		this.objectCrud.save(existing);
	}

	@Value("${spring.application.name}")
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	@Override
	@Deprecated
	public void bindObjects(String parentSystemId, String parentObjectId, ObjectChildIdBoundary child) {
		throw new BadRequestException("function bindObjects(String,String,ObjectChildIdBoundary) is deprecated!");
	}
	
	@Override
	public void bindObjects(String userSystemId,String email,String parentSystemId,String parentObjectId,ObjectChildIdBoundary child)
	{
		if(this.ul.getUser(UserService.genId(userSystemId, email)).getRole() != UserRole.OPERATOR) {
			throw new BadRequestException("Authorization failed!");
		}
		if (parentSystemId==null || parentObjectId==null || parentSystemId.isBlank() || parentObjectId.isBlank())
		{
			throw new InvalidInputException("Id cannot be null or empty!");
		}
		String parentId=UserService.genId(parentSystemId, parentObjectId);
		String childId=UserService.genId(child.getChildId().getSystemId(), child.getChildId().getId());
		ObjectEntity parent = this.objectCrud.findById(parentId).orElseThrow(()->new NotFoundException("Object not Found!"));
		ObjectEntity childEntity = this.objectCrud.findById(childId).orElseThrow(()->new NotFoundException("Object not Found!"));
		
		childEntity.setParent(parent);
		this.objectCrud.save(childEntity);
	}
	@Override
	@Deprecated
	public List<ObjectBoundary> getAllChildren(String parentSystemId,String parentObjectId) {
		throw new BadRequestException("function getAllChildren(String,String) is deprecated");
	}
	@Override
	@Transactional(readOnly=true)
	public List<ObjectBoundary> getAllChildren(String userSystemId,String userEmail,String parentSystemId,String parentObjectId,int size,int page)
	{
		UserRole ur = this.ul.getUser(UserService.genId(userSystemId, userEmail)).getRole();
		if (ur == UserRole.OPERATOR)
		{
			return this.objectCrud
				.findAllByParent_ObjectId(
						UserService.genId(parentSystemId, parentObjectId),
						PageRequest.of(page, size))
				.stream()
				.map(Converter::objectToBoundary)
				.toList();
		}else if(ur == UserRole.END_USER)
		{
			return this.objectCrud
					.findAllByParent_ObjectIdAndActiveTrue(
							UserService.genId(parentSystemId, parentObjectId),
							PageRequest.of(page, size))
					.stream()
					.map(Converter::objectToBoundary)
					.toList();
		}else
			throw new BadRequestException("Authorization failed!");
		
	}

	@Override
	public ObjectBoundary getParent(String childSystemId, String childObjectId) {
		ObjectEntity entity=objectCrud.findById(UserService.genId(childSystemId, childObjectId)).orElseThrow(()-> new NotFoundException("Object not found!"));
		return Converter.objectToBoundary(entity.getParent());
	}

	@Override
	@Transactional(readOnly=true)
	public List<ObjectBoundary> getAllByAlias(String userSystemId, String userEmail, String alias, int size, int page) {
		UserRole ur=this.ul.getUser(UserService.genId(userSystemId, userEmail)).getRole();
		if(ur == UserRole.OPERATOR)
		{
			return objectCrud.findAllByAlias(alias, PageRequest.of(page, size))
					.stream()
					.map(Converter:: objectToBoundary)
					.toList();
		}else if(ur == UserRole.END_USER)
		{
			return objectCrud.findAllByAliasAndActiveTrue(alias, PageRequest.of(page, size))
					.stream()
					.map(Converter:: objectToBoundary)
					.toList();
		}else
			throw new BadRequestException("Authorization failed!");
	}

	@Override
	public List<ObjectBoundary> getAllByType(String userSystemId, String userEmail, String type, int size, int page) {
		UserRole ur=this.ul.getUser(UserService.genId(userSystemId, userEmail)).getRole();
		if(ur == UserRole.OPERATOR)
		{
			return objectCrud.findAllByType(type, PageRequest.of(page, size))
					.stream()
					.map(Converter:: objectToBoundary)
					.toList();
		}else if(ur == UserRole.END_USER)
		{
			return objectCrud.findAllByTypeAndActiveTrue(type, PageRequest.of(page, size))
					.stream()
					.map(Converter:: objectToBoundary)
					.toList();
		}else
			throw new BadRequestException("Authorization failed!");
	}

	@Override
	public List<ObjectBoundary> getAllByTypeAndStatus(String userSystemId, String userEmail, String type,String status, int size,
			int page) {
		
		UserRole ur=this.ul.getUser(UserService.genId(userSystemId, userEmail)).getRole();
		if(ur == UserRole.OPERATOR)
		{
			return objectCrud.findAllByTypeAndStatus(type,status, PageRequest.of(page, size))
					.stream()
					.map(Converter:: objectToBoundary)
					.toList();
		}else if(ur == UserRole.END_USER)
		{
			return objectCrud.findAllByTypeAndStatusAndActiveTrue(type,status, PageRequest.of(page, size))
					.stream()
					.map(Converter:: objectToBoundary)
					.toList();
		}else
			throw new BadRequestException("Authorization failed!");
	}
	
	
	public List<ObjectBoundary> getAllByAliasPattern(String userSystemId, String userEmail, String aliasPattern, int size, int page) {
		UserRole ur=this.ul.getUser(UserService.genId(userSystemId, userEmail)).getRole();
		if(ur == UserRole.OPERATOR)
		{
			return objectCrud.findAllByAliasRegex(aliasPattern, PageRequest.of(page, size))
					.stream()
					.map(Converter:: objectToBoundary)
					.toList();
		}else if(ur == UserRole.END_USER)
		{
			return objectCrud.findAllByAliasRegexAndActiveTrue(aliasPattern, PageRequest.of(page, size))
					.stream()
					.map(Converter:: objectToBoundary)
					.toList();
		}else
			throw new BadRequestException("Authorization failed!");
	}
}
