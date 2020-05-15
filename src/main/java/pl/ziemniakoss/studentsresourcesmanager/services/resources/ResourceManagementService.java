package pl.ziemniakoss.studentsresourcesmanager.services.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import pl.ziemniakoss.studentsresourcesmanager.models.Resource;
import pl.ziemniakoss.studentsresourcesmanager.models.User;
import pl.ziemniakoss.studentsresourcesmanager.repositories.resources.IResourceRepository;
import pl.ziemniakoss.studentsresourcesmanager.repositories.users.IUserRepository;

import java.io.IOException;

@Service
public class ResourceManagementService {
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IResourceRepository resourceRepository;
	public void addFile(Resource resource, MultipartFile content) throws IOException {
		Assert.notNull(resource, "Dane o zasobach nie mogą być nullem");
		Assert.notNull(content,"Zasób jest niezbędny");
		Assert.isTrue(!content.isEmpty(), "Plik nie może być pusty");
		if(resource.getName() == null || resource.getName().equals("")){
			resource.setName(content.getOriginalFilename());
		}
		User user = userRepository.get(SecurityContextHolder.getContext().getAuthentication().getName());
		resourceRepository.addFile(resource, content.getInputStream().readAllBytes(), user);
		System.out.println(content.getOriginalFilename());
	}

}
