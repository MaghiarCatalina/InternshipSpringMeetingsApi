package edu.arobs.meetingsapi.proposal;

import edu.arobs.meetingsapi.exception.ProposalNotFoundException;
import edu.arobs.meetingsapi.exception.UserNotFoundException;
import edu.arobs.meetingsapi.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProposalService {
    private final ProposalRepository proposalRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProposalService(ProposalRepository proposalRepository, ModelMapper modelMapper,
                           UserRepository userRepository) {
        this.proposalRepository = proposalRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    public ProposalDTO create(ProposalDTO proposalDTO, Integer userId) {
        Proposal proposal = new Proposal();
        proposal.setId(null);
        proposal.setUser(userRepository.findById(userId).orElseThrow(UserNotFoundException::new));
        modelMapper.map(proposalDTO, proposal);
        Proposal savedProposal = proposalRepository.save(proposal);
        ProposalDTO savedProposalDTO = new ProposalDTO();
        modelMapper.map(savedProposal, savedProposalDTO);
        return savedProposalDTO;
    }

    @Transactional
    public ProposalDTO update(Integer id, ProposalDTO proposal, Integer userId) {
        Proposal updatedProposal = new Proposal();
        updatedProposal.setId(id);
        updatedProposal.setUser(userRepository.findById(userId).orElseThrow(UserNotFoundException::new));
        updatedProposal.setDescription(proposal.getDescription());
        updatedProposal.setDifficulty(proposal.getDifficulty());
        updatedProposal.setDuration(proposal.getDuration());
        updatedProposal.setLanguage(proposal.getLanguage());
        updatedProposal.setMaxPersons(proposal.getMaxPersons());
        updatedProposal.setAuthor(proposal.getAuthor());
        updatedProposal.setTitle(proposal.getTitle());
        updatedProposal.setType(proposal.getType());
        updatedProposal.setLastUpdated(Timestamp.valueOf(LocalDateTime.now()));
        proposalRepository.save(updatedProposal);
        ProposalDTO updatedProposalDTO = new ProposalDTO();
        modelMapper.map(updatedProposal, updatedProposalDTO);
        return updatedProposalDTO;
    }

    @Transactional
    public ProposalDTO getById(Integer id) {
        ProposalDTO proposalDTO = new ProposalDTO();
        Proposal existingProposal = proposalRepository.findById(id).orElseThrow(ProposalNotFoundException::new);
        modelMapper.map(existingProposal, proposalDTO);
        return proposalDTO;
    }

    @Transactional
    public ProposalDTO delete(Integer id) {
        ProposalDTO existingProposalDTO = getById(id);
        proposalRepository.deleteById(id);
        return existingProposalDTO;
    }

    @Transactional
    public List<ProposalDTO> getByUser(Integer id) {
        List<Proposal> foundProposals = proposalRepository.findByUser(id);
        List<ProposalDTO> foundProposalsDTO = new ArrayList<>();
        ProposalDTO proposalDTO = new ProposalDTO();
        if (foundProposals.isEmpty()) {
            throw new IllegalArgumentException(String.format("Proposals with user_id=%d not found", id));
        }
        for (Proposal proposal : foundProposals) {
            modelMapper.map(proposal, proposalDTO);
            foundProposalsDTO.add(proposalDTO);
        }
        return foundProposalsDTO;
    }

}
