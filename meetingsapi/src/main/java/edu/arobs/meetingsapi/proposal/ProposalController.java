package edu.arobs.meetingsapi.proposal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proposals")
public class ProposalController {

    private final ProposalService proposalService;

    @Autowired
    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @PostMapping
    public ProposalDTO create(Integer userId, @RequestBody ProposalDTO proposal) {
        return proposalService.create(proposal, userId);
    }

    @PutMapping("/{id}")
    public ProposalDTO update(@PathVariable Integer id, Integer userId, @RequestBody ProposalDTO proposal) {
        return proposalService.update(id, proposal, userId);
    }

    @GetMapping("/{id}")
    public ProposalDTO get(@PathVariable Integer id) {
        return proposalService.getById(id);
    }

    @GetMapping("/all/{id}")
    public List<ProposalDTO> getByUser(@PathVariable Integer id) {
        return proposalService.getByUser(id);
    }

    @DeleteMapping("/{id}")
    public ProposalDTO delete(@PathVariable Integer id) {
        return proposalService.delete(id);
    }

}
