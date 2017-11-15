package com.sporticus.web.services;

import com.sporticus.domain.entities.Group;
import com.sporticus.domain.entities.GroupMember;
import com.sporticus.domain.entities.Organisation;
import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.IGroupMember.Permission;
import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryGroup;
import com.sporticus.domain.repositories.IRepositoryGroupMember;
import com.sporticus.domain.repositories.IRepositoryUser;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceOrganisation;
import com.sporticus.services.dto.DtoGroup;
import com.sporticus.services.dto.DtoGroupMember;
import com.sporticus.services.dto.DtoGroupMemberOrdered;
import com.sporticus.services.dto.DtoList;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import com.sporticus.web.controllers.ControllerAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/api/ladder")
public class RestControllerLadder extends ControllerAbstract {

    private final static Logger LOGGER = LogFactory.getLogger (RestControllerLadder.class.getName ());

    private static final String GROUP_TYPE_LADDER = "LADDER";

    private final IRepositoryUser repositoryUser;

    private final IRepositoryGroup repositoryGroup;

    private final IRepositoryGroupMember repositoryGroupMember;

    private final IServiceGroup serviceGroup;

    private final IServiceOrganisation serviceOrganisation;

    @Autowired
    public RestControllerLadder(final IRepositoryUser repositoryUser,
                                final IRepositoryGroup repositoryGroup,
                                final IRepositoryGroupMember repositoryGroupMember,
                                final IServiceGroup serviceGroup,
                                final IServiceOrganisation serviceOrganisation) {
        this.repositoryUser = repositoryUser;
        this.repositoryGroup = repositoryGroup;
        this.repositoryGroupMember = repositoryGroupMember;
        this.serviceGroup = serviceGroup;
        this.serviceOrganisation = serviceOrganisation;
    }

    @PostConstruct
    private void init(){
        // check for groups - if there are none then create one and make all users member of the new group
        DtoList<DtoGroup> groupsLadder = findLadderGroups(null);
        if(groupsLadder.getLength()==0){
            IOrganisation org = null;
            List<IUser> users = serviceUser.getAll();
            if(users.size()>0) {
                List<IOrganisation> orgs = serviceOrganisation.readAllOrganisations();
                if (orgs.size() == 0) {
                    org = new Organisation();
                    org.setEnabled(true);
                    org.setCreated(new Date());
                    org.setAddress("Somewhere town");
                    org.setName("Test Organisation");
                    org.setOwnerId(users.get(0).getId());
                    org.setDomain("domain.com");
                    org = serviceOrganisation.createOrganisation(org);
                } else {
                    org = orgs.get(0);
                }
                IGroup group = new Group();
                group.setType(GROUP_TYPE_LADDER);
                group.setName("Ladder Group");
                group.setEnabled(true);
                group.setDescription("A ladder group");
                group.setOwnerOrganisationId(org.getId());

                group = serviceGroup.createGroup(org, group);
                for (IUser user : users) {
                    GroupMember gm = new GroupMember();

                    gm.setCreated(new Date());
                    gm.setEnabled(true);
                    gm.setAcceptedOrRejectedDate(new Date());
                    gm.setGroupId(group.getId());
                    gm.setUserId(user.getId());
                    gm.setPermissions(Permission.WRITE);

                    repositoryGroupMember.save(gm);
                }
            }
        }
    }

    private DtoList<DtoGroup> findLadderGroups(Long userId){
        final DtoList<DtoGroup> list = new DtoList();
        this.serviceGroup.readGroups(gm -> (userId==null || gm.getUserId().equals(userId)) &&
                gm.getStatus().equals(IGroupMember.Status.Accepted) &&
                gm.isEnabled())
                .stream()
                .filter(g-> g.getType().equalsIgnoreCase(GROUP_TYPE_LADDER))
                .forEach(g -> list.add(convertToDtoGroup(g)));
        return list;
    }

    private DtoGroup convertToDtoGroup(final IGroup g) {
        final DtoGroup dtoGroup = new DtoGroup(g);
        final List<IUser> users = serviceGroup.getMembershipUsersForGroup(g.getId(), null);
        dtoGroup.setCountMembers(Long.valueOf(users.size()));
        return dtoGroup;
    }

    /**
     * Function construct the DtoGroupMemberOrdered
     *
     * @param gm
     * @return DtoGroupMemberOrdered
     */
    private DtoGroupMemberOrdered convertToDtoGroupMemberLadder (final IGroupMember gm) {
        final DtoGroupMemberOrdered dtoGroupMemberLadder = new DtoGroupMemberOrdered(gm);
        final IUser u = repositoryUser.findOne (gm.getUserId ());
        if (u != null) {
            dtoGroupMemberLadder.setEmail (u.getEmail ());
            dtoGroupMemberLadder.setUserName (u.getFirstName () + " " + u.getLastName ());
            dtoGroupMemberLadder.setFirstName (u.getFirstName ());
            dtoGroupMemberLadder.setLastName (u.getLastName ());
        }
        final IGroup g = repositoryGroup.findOne (gm.getGroupId ());
        if (g != null) {
            dtoGroupMemberLadder.setGroupName (g.getName ());
            dtoGroupMemberLadder.setGroupDescription (g.getDescription ());
        }
        return dtoGroupMemberLadder;
    }

    private DtoList<DtoGroupMemberOrdered> convertToDtoGroupMembers (final List<IGroupMember> list) {
        final DtoList<DtoGroupMemberOrdered> out = new DtoList<> ();
        list.forEach (gm -> {
            DtoGroupMemberOrdered gml =convertToDtoGroupMemberLadder(gm);
            gml.setPosition(list.indexOf(gm));
            out.add(gml);
        });
        return out;
    }

    /***
     * Function returns a list of the group memberships (of type ladder) for a user
     * (i.e. it returns a list of ladders that the user is member of)
     * (only those groups that are enabled and accepted)
     * @return ResponseEntity<DtoGroups>
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoList<DtoGroup>> readLadderGroupsForLoggedUser() {
        long userId = this.getLoggedInUserId();
        return new ResponseEntity<>(findLadderGroups(userId), HttpStatus.OK);
    }

    /**
     * Function returns a group given an identifier
     *
     * @param id
     * @return ResponseEntity<DtoGroup>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoGroup> readLadderGroup(@PathVariable("id") final long id) {
        if(id == -1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.debug(() -> "Reading Ladder Group with id " + id);
        final Optional<IGroup> found = serviceGroup.readGroup(id);
        if(!found.isPresent()) {
            LOGGER.warn(() -> "Group not found - id=" + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // TODO: check it is a ladder group
        return new ResponseEntity<>(convertToDtoGroup(found.get()), HttpStatus.OK);
    }

    /**
     * Function returns a group's members given an identifier
     *
     * @param id
     * @return ResponseEntity<DtoGroup>
     */
    @RequestMapping(value = "/{id}/members", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoList<DtoGroupMemberOrdered>> read(@PathVariable("id") final long id) {
        if(id == -1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.debug(() -> "Reading Ladder Group Members - groupId=" + id);
        final Optional<IGroup> found = serviceGroup.readGroup(id);
        if(!found.isPresent()) {
            LOGGER.warn(() -> "Group not found - id=" + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(convertToDtoGroupMembers(serviceGroup.getGroupMembershipsForGroup(id)), HttpStatus.OK);
    }
}