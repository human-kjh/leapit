package com.example.leapit.positionType;


import com.example.leapit.common.positiontype.PositionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(PositionTypeRepository.class)
@DataJpaTest
public class PositionTypeRepositoryTest {

    @Autowired
    private PositionTypeRepository positionTypeRepository;

//    @Test
//    public void findAllLabel() {
//        // given
//        String selectedLabel = "백엔드";
//
//        // when
//        List<PositionTypeResponse.PositionTypeDTO> labels = positionTypeRepository.findAllLabelAndSelectedLabel(selectedLabel);
//
//        // eye
//        for (PositionTypeResponse.PositionTypeDTO labelDto : labels) {
//            System.out.println(labelDto.getLabel());
//        }
//    }
}
