package ru.itmo.alkarized.lab4.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity @Getter @Setter
@Table(name = "pointTable")
public class Point implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int x;
    private double y;
    private int r;
    private String result;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "id_user")
    private User user;

    public void setResult(){
        if((x >=0 && y >= 0 && y <= r && x <= r/2) ||
                (x >= 0 && y<= 0 && y>= - Math.sqrt(r*r-4*x*x)/2) ||
                (x<=0 && y >=0 && y<= (double)r/2 + x)){
            result = "hit";
        } else result = "non-hit";
    }

    @Override
    public String toString(){
        JSONObject jsonObject = new JSONObject();
        return jsonObject
                .put("x", x)
                .put("y", y)
                .put("r", r)
                .put("result", result)
                .put("id", id).toString();
    }

    public Point() {
    }

    public Point(int x, double y, int r, String result) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }
}
