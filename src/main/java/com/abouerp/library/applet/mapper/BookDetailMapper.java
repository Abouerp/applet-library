package com.abouerp.library.applet.mapper;

import com.abouerp.library.applet.domain.book.BookDetail;
import com.abouerp.library.applet.dto.BookDetailDTO;
import com.abouerp.library.applet.vo.BookDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Abouerp
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookDetailMapper {
    BookDetailMapper INSTANCE = Mappers.getMapper(BookDetailMapper.class);

    BookDetail toBookDetale(BookDetailVO bookDetailVO);

    BookDetailDTO toDTO(BookDetail bookDetail);
}
