package robotic.system.forum.domain.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;
import java.util.UUID;
;

@Getter
@Setter
@Entity
public class Tag {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    @JsonBackReference
    private List<Forum> forums;
}