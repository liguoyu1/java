package server.serverTest;

import java.util.ArrayList;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import Docker.Docker;
import Mysql.Select;

@Path("/docker")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DokcerResource {
	/**
	 * 提交docker 镜像的表单
	 * @return
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/submit")
	public boolean submitDocker(){
		return false;
	}
	
	/**
	 * 获取 所有docker 信息
	 * @return
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/allDocker")
	public JsonDockerList getAllDocker(){
		System.out.println("Get All docker images!");
		JsonDockerList jdockerList = null;
		ArrayList<Docker> dockerlist = null;
		Select select = new Select();
		dockerlist = select.selectAllDocker();
		if(dockerlist.isEmpty()||dockerlist.size() == 0){
			return jdockerList;
		}
		jdockerList = new JsonDockerList();
		for(Iterator<Docker> iter = dockerlist.iterator();iter.hasNext();){
			jdockerList.addDocker(new JsonDocker(iter.next()));
		}
		jdockerList.show();
		return jdockerList;
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/docker/{docker}")
	public JsonDocker getDockerByName(@PathParam("docker") String dockerName){
		System.out.println("Get docker image by dockerName!");
		JsonDocker jdocker = null;
		Docker docker;
		Select select = new Select();
		docker = select.selectDockerByName(dockerName);
		if(docker == null){
			return jdocker;
		}
		jdocker = new JsonDocker();
		return jdocker;
	}
}
