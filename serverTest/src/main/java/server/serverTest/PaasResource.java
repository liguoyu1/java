package server.serverTest;

import java.util.ArrayList;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import CloudR.Resource;
import Mysql.Select;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("resource")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PaasResource {
	/**
	 * 获取所有资源列表
	 * @return
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/allRe")
	public JsonResourceList getAllResource(){
		Select select = new Select();
		JsonResourceList jResources = new JsonResourceList();
		ArrayList<Resource> relist = select.selectAllResouce();
		for(Iterator<Resource> iter = relist.iterator();iter.hasNext();){
			JsonResource jre = new JsonResource(iter.next());
			System.out.println("Resource Id:"+jre.getResourceId()+"\t Resource place:"+jre.getResourcePlace());
			jResources.addResource(jre);
		}
		System.out.println("Get All Resource!");
		System.out.println(jResources.getJsonResourceList().size());
		return jResources;	
	}
	
	/**
	 * 根据资源id 获取资源节点信息
	 * @param reId
	 * @return
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/reId/{reId}")
	public JsonResource getJsonResourceById(@PathParam("reId") long reId){
		System.out.println("Get Resource by Id!");
		JsonResource jre = null;
		Select select = new Select();
		jre = new JsonResource(select.selectResource(reId));
		return jre;
	}

	/**
	 * 根据资源所在地 获取资源信息
	 * @param resourcePlace
	 * @return
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/resourcePlace/{resourcePlace}")
	public JsonResourceList getJsonResourceByPlace(@PathParam("resourcePlace") String resourcePlace){
		System.out.println("Get Resource by Place!");
		JsonResourceList jrel = new JsonResourceList();
		Select select = new Select();
		ArrayList<Resource> relist = select.selectResourceByPlace(resourcePlace);
		System.out.println(relist.size());
		for(Iterator<Resource> iter = relist.iterator();iter.hasNext();){
			jrel.addResource(new JsonResource(iter.next()));
		}
		return jrel;
	}

}
