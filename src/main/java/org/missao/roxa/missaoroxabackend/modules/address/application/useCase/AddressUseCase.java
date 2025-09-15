package org.missao.roxa.missaoroxabackend.modules.address.application.useCase;

import lombok.Getter;
import org.missao.roxa.missaoroxabackend.modules.address.application.useCase.changeComplement.IAddressChangeComplement;
import org.missao.roxa.missaoroxabackend.modules.address.application.useCase.changeNeighbordhood.IAddressChangeNeighborhood;
import org.missao.roxa.missaoroxabackend.modules.address.application.useCase.changePostalCode.IAddressChangePostalCode;
import org.missao.roxa.missaoroxabackend.modules.address.application.useCase.changeStreet.IAddressChangeStreet;
import org.missao.roxa.missaoroxabackend.modules.address.application.useCase.find.IAddressFind;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AddressUseCase {

    private final IAddressFind find;
    private final IAddressChangePostalCode changePostalCode;
    private final IAddressChangeStreet changeStreet;
    private final IAddressChangeComplement changeComplement;
    private final IAddressChangeNeighborhood changeNeighborhood;


    public AddressUseCase(IAddressFind find, IAddressChangePostalCode changePostalCode, IAddressChangeStreet changeStreet,
                          IAddressChangeComplement changeComplement, IAddressChangeNeighborhood changeNeighborhood) {
        this.find = find;
        this.changePostalCode = changePostalCode;
        this.changeStreet = changeStreet;
        this.changeComplement = changeComplement;
        this.changeNeighborhood = changeNeighborhood;
    }

}