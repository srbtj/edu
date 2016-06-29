package com.syzton.sunread.controller.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javassist.NotFoundException;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syzton.sunread.controller.BaseController;
import com.syzton.sunread.controller.util.SecurityContextUtil;
import com.syzton.sunread.dto.campus.CampusOrderDTO;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.dto.user.UserDTO;
import com.syzton.sunread.dto.user.UserExtraDTO;
import com.syzton.sunread.model.region.SchoolDistrict;
import com.syzton.sunread.model.task.Task;
import com.syzton.sunread.model.user.Parent;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.Teacher;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.service.bookshelf.BookshelfService;
import com.syzton.sunread.service.user.UserService;

/**
 * Created by jerry on 3/9/15.
 */
@Controller
@RequestMapping(value = "/api")
public class UserController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    private UserService userService;

    private DefaultTokenServices tokenServices;

    private PasswordEncoder passwordEncoder;

    private ClientDetailsService clientDetailsService;

    private BookshelfService bookshelfService;

    @Resource
    private SecurityContextUtil contextUtil;

    @Autowired
    public void setReviewService(UserService userService, DefaultTokenServices tokenServices,
                                 PasswordEncoder passwordEncoder,
                                 ClientDetailsService clientDetailsService,
                                 BookshelfService bookshelfService) {
        this.userService = userService;
        this.tokenServices = tokenServices;
        this.passwordEncoder = passwordEncoder;
        this.clientDetailsService = clientDetailsService;
        this.bookshelfService = bookshelfService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public UserDTO add(@Valid @RequestBody User user) {
        LOGGER.debug("PASSWORD:" + user.getPassword());
        User insertUser = userService.addUser(user);
        return new UserDTO(insertUser, createTokenForNewUser(
                insertUser.getUserId(), insertUser.getPassword(),
                "353b302c44574f565045687e534e7d6a", "ROLE_USER"));
    }

    @RequestMapping(value = "/user/fromtoken", method = RequestMethod.GET)
    @ResponseBody
    public User add() {
        User user = contextUtil.getUser();
        LOGGER.debug(user.getUserId());
        return user;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteById(@PathVariable("id") Long id) {

        userService.deleteById(id);

    }

    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/users/test/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void test(@PathVariable("id") Long id) {


    }

    @RequestMapping(value = "/tokens/{token}", method = RequestMethod.POST)
    public void verifyToken(@PathVariable("token") String token) {
//        verificationTokenService.verify(token);
//        return Response.ok().build();
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    @ResponseBody
    public Student add(@Valid @RequestBody Student student) {
        Student added = userService.addStudent(student);
    	bookshelfService.addBookshelfByStudent(added);
        return added;
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    @ResponseBody
    public User updateUserExtra(@PathVariable("userId") long userId,
                                @Valid @RequestBody UserExtraDTO userExtraDTO) {
        return userService.updateUser(userId, userExtraDTO);
    }

    @RequestMapping(value = "teachers/{teacherId}/students/{studentId}/tasks", method = RequestMethod.PUT)
    @ResponseBody
    public Task addTask(@PathVariable("teacherId") long teacherId,
                       @PathVariable("studentId") long studentId,
                       @RequestParam("targetBookNum") int targetBookNum,
                       @RequestParam("targetPoint") int targetPoint) {
        return userService.addTask(teacherId, studentId, targetBookNum, targetPoint);
    }
    
    @RequestMapping(value = "teachers/{teacherId}/tasks", method = RequestMethod.PUT)
    @ResponseBody
    public void addTasks(@PathVariable("teacherId") long teacherId,
                            @RequestParam("targetBookNum") int targetBookNum,
                            @RequestParam("targetPoint") int targetPoint) {
        userService.addTasks(teacherId, targetBookNum, targetPoint);
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteByStudentId(@PathVariable("id") Long id) {

        Student student = userService.deleteByStudentId(id);
        bookshelfService.deleteBookshelfByStudent(student);

    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Student findByStudentId(@PathVariable("id") Long id) {
        return userService.findByStudentId(id);
    }

    /*student add parent*/
    //TODO Student role
    @RequestMapping(value = "/students/{id}/parents", method = RequestMethod.POST)
    @ResponseBody
    public Parent add(@Valid @RequestBody Parent parent, @PathVariable("id") Long id) {
        return userService.addParent(parent, id);
    }

    /*parent add student*/
    //TODO Parent role
    @RequestMapping(value = "/parents/{id}/students/{userId}", method = RequestMethod.PUT)
    @ResponseBody
    public Parent addChildren(@PathVariable("id") Long id, @PathVariable("userId") String userId) {

        return userService.addChildren(id, userId);
    }


    @RequestMapping(value = "/teachers", method = RequestMethod.POST)
    @ResponseBody
    public Teacher addTeacher(@Valid @RequestBody Teacher teacher) {
        return userService.addTeacher(teacher);
    }

    @RequestMapping(value = "/teachers/{teacherId}", method = RequestMethod.GET)
    @ResponseBody
    public Teacher findByTeacherId(@PathVariable("teacherId") Long teacherId) {
        return userService.findByTeacherId(teacherId);
    }
    
    @RequestMapping(value = "/campuses/{campusId}/teachers", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Teacher> findTeacherByCampusId(@PathVariable("campusId") Long campusId,
                                         @RequestParam("page") int page,
                                         @RequestParam("size") int size,
                                         @RequestParam(value = "sortBy", required = false) String sortBy,
                                         @RequestParam(value = "direction", required = false) String direction) {
        Pageable pageable = this.getPageable(page,size);
        Page<Teacher> teacherPage = userService.findTeacherByCampusId(campusId,pageable);
        return  new PageResource<>(teacherPage,"page","size") ;
    }

    @RequestMapping(value = "/teachers/search", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Teacher> searchCampusQuestions(@RequestParam("name") String name,@RequestParam("campusid") long campusid,@RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy) throws NotFoundException {
    	sortBy = sortBy==null?"id": sortBy;
        
        Pageable pageable = new PageRequest(page,size,new Sort(sortBy));
        Page<Teacher> pageResult = userService.searchTeachersByName(name,campusid,pageable);

        return new PageResource<>(pageResult,"page","size");
    }

    @RequestMapping(value = "/teachers/{teacherId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteTeacherId(@PathVariable("teacherId") Long teacherId) {
        userService.deleteByTeacherId(teacherId);
    }

    @RequestMapping(value = "/campus/{campusId}/hotreaders", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Student> hotReadersInCampus(@PathVariable("campusId") Long campusId,
                                            @RequestParam("page") int page,
                                            @RequestParam("size") int size,
                                            @RequestParam(value = "sortBy", required = false) String sortBy,
                                            @RequestParam(value = "direction", required = false) String direction) {
    	
    	sortBy = sortBy == null ? "statistic.point" : "statistic." + sortBy;
        Pageable pageable = this.getPageable(page, size, sortBy, "desc");
        Page<Student> hotReaders = userService.hotReadersInCampus(campusId, pageable);

        return new PageResource<>(hotReaders,"page","size") ;


    }
    @RequestMapping(value = "/students/campuses/{campusId}/orders", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Student> ordersInCampus(@PathVariable("campusId") Long campusId,
                                            @RequestParam("page") int page,
                                            @RequestParam("size") int size,
                                            @RequestParam(value = "sortBy", required = false) String sortBy,
                                            @RequestParam(value = "direction", required = false) String direction) {
        sortBy = sortBy == null ? "statistic.point" : sortBy;

        Pageable pageable = this.getPageable(page,size,sortBy,direction);
        Page<Student> hotReaders = userService.hotReadersInCampus(campusId, pageable);

        return new PageResource<>(hotReaders,"page","size") ;


    }

    @RequestMapping(value = "/students/clazzs/{clazzId}/orders", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Student> ordersInClazz(@PathVariable("clazzId") Long clazzId,
                                                    @RequestParam("page") int page,
                                                    @RequestParam("size") int size,
                                                    @RequestParam(value = "sortBy", required = false) String sortBy,
                                                    @RequestParam(value = "direction", required = false) String direction) {
        sortBy = sortBy == null ? "statistic.point" : "statistic." + sortBy;
        Pageable pageable = this.getPageable(page,size,sortBy,direction);
        Page<Student> hotReaders = userService.hotReadersInClazz(clazzId, pageable);

        return new PageResource<>(hotReaders,"page","size") ;


    }
    @RequestMapping(value = "/students/grades/{gradeId}/orders", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Student> ordersInGrade(@PathVariable("gradeId") Long gradeId,
                                                   @RequestParam("page") int page,
                                                   @RequestParam("size") int size,
                                                   @RequestParam(value = "sortBy", required = false) String sortBy,
                                                   @RequestParam(value = "direction", required = false) String direction) {
        sortBy = sortBy == null ? "statistic.point" : sortBy;
        Pageable pageable = this.getPageable(page,size,sortBy,direction);
        Page<Student> hotReaders = userService.hotReadersInGrade(gradeId, pageable);

        return new PageResource<>(hotReaders,"page","size") ;


    }
    
    @RequestMapping(value = "/campus/grade/{grade}/orders", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<CampusOrderDTO> campusOrdersInGrade( @PathVariable("grade") int grade,
		                                                     @RequestParam("page") int page,
		                                                     @RequestParam("size") int size,
		                                                     @RequestParam(value = "sortBy", required = false) String sortBy,
		                                                     @RequestParam(value = "direction", required = false) String direction,
		                                                     @RequestParam(value = "eduGroupId", required = false) Long eduGroupId,
		                                                     @RequestParam(value = "schoolDistrictId", required = false) Long schoolDistrictId) {
        sortBy = sortBy == null ? "point" : sortBy;
        Pageable pageable = this.getPageable(page, size, sortBy, direction);
        Page<CampusOrderDTO> hotCampuses = null;
        if (schoolDistrictId != null) {
        	hotCampuses = userService.hotCampusesInGradeAndSchoolDistrict( grade, schoolDistrictId, page, size, sortBy, direction, pageable);
        }
        if (eduGroupId != null) {
        	hotCampuses = userService.hotCampusesInGradeAndEduGroup( grade, eduGroupId, page, size, sortBy, direction, pageable);
        }
        
        return new PageResource<>(hotCampuses,"page","size") ;


    }
    
    @RequestMapping(value = "/clazz/{clazzId}/hotreaders", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Student> hotReadersInClazz(@PathVariable("clazzId") Long clazzId,
                                            @RequestParam("page") int page,
                                            @RequestParam("size") int size,
                                            @RequestParam("sortBy") String sortBy) {

        Pageable pageable = this.getPageable(page,size,"statistic." + sortBy ,"desc");
        Page<Student> hotReaders = userService.hotReadersInClazz(clazzId,pageable);

        return new PageResource<>(hotReaders,"page","size") ;


    }
    
    @RequestMapping(value = "/superadmin", method = RequestMethod.GET)
    @ResponseBody
    public String hotReadersInClazz() {
    	return userService.addSuperSystemAdmin("admin", "123456");
    }

    private OAuth2AccessToken createTokenForNewUser(String username,
                                                    String password, String clientId, String role) {
        String hashedPassword = passwordEncoder.encode(password);
        UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(
                username, hashedPassword,
                Collections.singleton(new SimpleGrantedAuthority(role)));
        ClientDetails authenticatedClient = clientDetailsService
                .loadClientByClientId(clientId);
        OAuth2Request oAuth2Request = createOAuth2Request(null, clientId,
                Collections.singleton(new SimpleGrantedAuthority(role)), true,
                authenticatedClient.getScope(), null, null, null, null);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(
                oAuth2Request, userAuthentication);
        return tokenServices.createAccessToken(oAuth2Authentication);
    }

    private OAuth2Request createOAuth2Request(
            Map<String, String> requestParameters, String clientId,
            Collection<? extends GrantedAuthority> authorities,
            boolean approved, Collection<String> scope,
            Set<String> resourceIds, String redirectUri,
            Set<String> responseTypes,
            Map<String, Serializable> extensionProperties) {
        return new OAuth2Request(requestParameters, clientId, authorities,
                approved, scope == null ? null : new LinkedHashSet<String>(
                scope), resourceIds, redirectUri, responseTypes,
                extensionProperties);
    }

}
